package com.dabai.proxy.facade.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.DesensitizedUtil;
import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.contants.DabaiContants;
import com.dabai.proxy.enums.CashStatusEnum;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.exception.HttpClientBusinessException;
import com.dabai.proxy.facade.CashFacade;
import com.dabai.proxy.httpclient.alipay.AlipayHttpClient;
import com.dabai.proxy.httpclient.alipay.resp.ValidateCardInfoResp;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;
import com.dabai.proxy.httpclient.liness.LinessHttpClient;
import com.dabai.proxy.httpclient.liness.param.AddMerchantParam;
import com.dabai.proxy.httpclient.liness.param.SignAgreementParam;
import com.dabai.proxy.httpclient.liness.param.TransferToBankCardParam;
import com.dabai.proxy.httpclient.liness.resp.AddMerchantResult;
import com.dabai.proxy.httpclient.liness.resp.LinessBaseResult;
import com.dabai.proxy.httpclient.liness.resp.SignAgreementResult;
import com.dabai.proxy.httpclient.liness.resp.TransferToBankCardResult;
import com.dabai.proxy.po.CashSnapshot;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserSignInfo;
import com.dabai.proxy.po.WalletInfo;
import com.dabai.proxy.req.CashInfoPageReq;
import com.dabai.proxy.req.UserCashSubmitReq;
import com.dabai.proxy.req.UserSignReq;
import com.dabai.proxy.resp.CashInfoPageResult;
import com.dabai.proxy.resp.CashInfoResp;
import com.dabai.proxy.resp.UserCashSignInfoResp;
import com.dabai.proxy.service.CashSnapshotService;
import com.dabai.proxy.service.UserInfoService;
import com.dabai.proxy.service.UserSignInfoService;
import com.dabai.proxy.service.WalletInfoService;
import com.dabai.proxy.utils.JsonUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:43
 */
@Service
@Slf4j
public class CashFacadeImpl implements CashFacade {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserSignInfoService userSignInfoService;
    @Autowired
    private WalletInfoService walletInfoService;
    @Resource
    private LinessHttpClient linessHttpClient;
    @Resource
    private CashSnapshotService cashSnapshotService;
    @Resource
    private HuanongHttpClient huanongHttpClient;
    @Resource
    private AlipayHttpClient alipayHttpClient;
    @Value("${domain}")
    private String domain;

    @Value("${http.liness.transferCorpId}")
    private String transferCorpId;

    @Value("${http.liness.agreementType}")
    private String agreementType;

    @Override
    public UserCashSignInfoResp cashInfo(String openId) {
        Assert.notNull(openId, "openId??????");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        if (userInfo == null) {
            return null;
        }
        return buildSignInfoResp(userInfo, null, null);
    }

    @Override
    public UserCashSignInfoResp sign(String openId, UserSignReq userSignReq) {
        Assert.notNull(openId, "openId??????");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        Assert.notNull(userInfo, "????????????????????????????????????");
        checkSignReq(userSignReq, userInfo);

        UserSignInfo userSignInfo = userSignInfoService.getByUserId(userInfo.getId());

        if (Objects.equals(userInfo.getBankCard(), userSignReq.getBankCard()) &&
                Objects.equals(userInfo.getName(), userSignReq.getName()) &&
                Objects.equals(userInfo.getIdCard(), userSignReq.getIdCard())) {
            if (userSignInfo != null && (userSignInfo.getSignStatus().equals(UserSignStatusEnum.SUCCESS.getCode())
                    || userSignInfo.getSignStatus().equals(UserSignStatusEnum.SIGNING.getCode()))) {
                log.info("????????????????????????????????????????????? ?????????????????????[userSignReq:{}]", userSignReq);
                return buildSignInfoResp(userInfo, UserSignStatusEnum.getByCode(userSignInfo.getSignStatus()), null);
            }
        }
        log.info("???????????? ??????????????????[userSignReq:{}]", userSignReq);
        // ????????????
        AddMerchantParam addMerchantParam = new AddMerchantParam();
        addMerchantParam.setCertificateType("ID");
        addMerchantParam.setCertificateNo(userSignReq.getIdCard());
        addMerchantParam.setName(userSignReq.getName());
        addMerchantParam.setMobileNo(userSignReq.getMobile());
        LinessBaseResult<AddMerchantResult> addMerchantResult = linessHttpClient.addMerchant(addMerchantParam);
        log.info("??????????????? [addMerchantParam:{}, result={}]", addMerchantParam, addMerchantResult);
        if (addMerchantResult == null || !LinessHttpClient.SUCCESS_CODE.equals(addMerchantResult.getRetCode())) {
            throw new HttpClientBusinessException("???????????????????????????????????????");
        }
        // ??????
        SignAgreementParam signAgreementParam = new SignAgreementParam();
        signAgreementParam.setName(userSignReq.getName());
        signAgreementParam.setCertificateNo(userSignReq.getIdCard());
        signAgreementParam.setCertificateType("1");
        signAgreementParam.setNotifyUrl(domain + DabaiContants.ZX_SIGN_CALL_BACK_URL);
        signAgreementParam.setSerialNo(String.valueOf(System.currentTimeMillis()));
        signAgreementParam.setMobileNo(userSignReq.getMobile());
        signAgreementParam.setTransferCorpId(transferCorpId);
        signAgreementParam.setSignAgreementType(agreementType);

        LinessBaseResult<SignAgreementResult> signAgreement = linessHttpClient.signAgreement(signAgreementParam);
        log.info("?????? [signAgreementParam:{}, result={}]", signAgreementParam, signAgreement);

        if (Objects.isNull(signAgreement) || !LinessHttpClient.SUCCESS_CODE.equals(signAgreement.getRetCode())) {
            throw new HttpClientBusinessException("??????????????????????????????");
        }

        //????????????????????????????????????
        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(userSignReq.getMobile());
        memberInfoParam.setIdCardNo(userSignReq.getIdCard());
        memberInfoParam.setName(userSignReq.getName());
        try {
            HuanongResult<MemberInfoResp> result = huanongHttpClient.memberInfo(memberInfoParam);
            if (result == null || !Objects.equals(result.getState(), "200")) {
                log.error("??????????????????????????????, memberInfoParam:{}, result:{}", memberInfoParam, result);
            }
        } catch (Exception e) {
            log.error("??????????????????????????????, memberInfoParam:" + memberInfoParam, e);
        }

        SignAgreementResult retData = signAgreement.getRetData();
        CashFacadeImpl cashFacade = (CashFacadeImpl) AopContext.currentProxy();
        return cashFacade.saveSignInfo(userInfo.getId(), userSignReq, retData);
    }

    @Override
    public Result<String> cashSubmit(String openId, UserCashSubmitReq cashSubmitReq) {
        Assert.notNull(openId, "openId??????");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        Assert.notNull(userInfo, "????????????????????????????????????");
        checkSubmitReq(cashSubmitReq, userInfo);
        String requestNo = String.valueOf(System.currentTimeMillis());

        TransferToBankCardParam transferToBankCardParam = new TransferToBankCardParam();
        transferToBankCardParam.setBankCardNo(userInfo.getBankCard());
        transferToBankCardParam.setName(userInfo.getName());
        transferToBankCardParam.setTransferCorpId(transferCorpId);
        transferToBankCardParam.setRequestNo(requestNo);
        transferToBankCardParam.setAmount(cashSubmitReq.getAmount().toString());
        transferToBankCardParam.setCertificateNo(userInfo.getIdCard());
        transferToBankCardParam.setNotifyUrl(domain + DabaiContants.ZX_CASH_CALL_BACK_URL);
        transferToBankCardParam.setRemark("??????????????????");

        CashSnapshot cashSnapshot = cashSnapshotService.init(userInfo.getId(), userInfo.getMobile(), transferToBankCardParam);
        LinessBaseResult<TransferToBankCardResult> transferToBankcard = linessHttpClient.transferToBankcard(transferToBankCardParam);
        log.info("?????? [transferToBankCardParam:{}, result={}]", transferToBankCardParam, transferToBankcard);

        if (Objects.isNull(transferToBankcard)) {
            throw new HttpClientBusinessException("??????????????????????????????");
        }

        // ??????????????????
        if (LinessHttpClient.SUCCESS_CODE.equals(transferToBankcard.getRetCode())) {
            TransferToBankCardResult retData = transferToBankcard.getRetData();
            log.info("retData:{}",retData);
            cashSnapshot.setStatus(CashStatusEnum.CASHING.getCode());
            cashSnapshot.setDealNo(retData.getDealNo());
            cashSnapshot.setRemark(retData.getDealStatusMsg());
            walletInfoService.cashing(userInfo.getId(), cashSnapshot);
            // ??????????????????
            LocalCache.invalidateCode(userInfo.getMobile());
            return Result.success(retData.getDealNo());
        }
        // ??????????????????
        String thirdResp = JsonUtils.toJson(transferToBankcard);
        String remark = transferToBankcard.getRetMsg();
        if ("T022".equals(transferToBankcard.getRetCode())) {
            remark = "?????????????????????";
        }
        //?????????????????????????????? :)
        if (transferToBankcard.getRetMsg().contains("?????????")) {
            remark = "??????????????????????????? 9.7 ???";
        }
        cashSnapshotService.cashFailed(cashSnapshot.getId(), remark, thirdResp);
        return Result.genResult(-1, remark, null);
    }

    @Override
    public CashInfoPageResult pageQuery(CashInfoPageReq cashInfoPageReq) {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        UserInfo userInfo = userInfoService.selectByOpenId(sessionInfo.getOpenId());
        Assert.notNull(userInfo, "??????????????????????????????");

        Page<CashSnapshot> pageResult = PageHelper.offsetPage(cashInfoPageReq.getPaging().getOffset(), cashInfoPageReq.getPaging().getLimit())
                .doSelectPage(() -> cashSnapshotService.pageQuery(userInfo.getId(), cashInfoPageReq.getCashInfoId()));
        CashInfoPageResult resp = new CashInfoPageResult();
        resp.setTotal(pageResult.getTotal());
        List<CashSnapshot> result = pageResult.getResult();
        List<CashInfoResp> cashInfoRespList = result.stream().map(e -> {
            CashInfoResp cashInfoResp = new CashInfoResp();
            BeanUtils.copyProperties(e, cashInfoResp);
            return cashInfoResp;
        }).collect(Collectors.toList());
        resp.setList(cashInfoRespList);
        return resp;
    }

    @Transactional(rollbackFor = Throwable.class)
    public UserCashSignInfoResp saveSignInfo(Long userId, UserSignReq userSignReq, SignAgreementResult retData) {
        userInfoService.saveSignInfo(userId, userSignReq.getBankName(), userSignReq.getBankCard(), userSignReq.getIdCard(),
                userSignReq.getName(), userSignReq.getMobile());
        userSignInfoService.signing(userId, retData.getDealNo());
        UserInfo userInfo = userInfoService.selectById(userId);
        return buildSignInfoResp(userInfo, UserSignStatusEnum.SIGNING, null);
    }

    private UserCashSignInfoResp buildSignInfoResp(UserInfo userInfo, UserSignStatusEnum signStatus, WalletInfo walletInfo) {
        UserCashSignInfoResp userCashSignInfoResp = new UserCashSignInfoResp();
        BeanUtils.copyProperties(userInfo, userCashSignInfoResp);
        if (Objects.isNull(signStatus)) {
            UserSignInfo userSignInfo = userSignInfoService.getByUserId(userInfo.getId());
            if (Objects.isNull(userSignInfo)) {
                signStatus = UserSignStatusEnum.NOT_SIGN;
            } else {
                signStatus = UserSignStatusEnum.getByCode(userSignInfo.getSignStatus());
            }
        }
        userCashSignInfoResp.setIdCard(DesensitizedUtil.idCardNum(userInfo.getIdCard(),3,4));
        userCashSignInfoResp.setBankCard(DesensitizedUtil.bankCard(userInfo.getBankCard()));
        if (Objects.nonNull(signStatus)) {
            userCashSignInfoResp.setSignStatus(signStatus.getCode());
        }

        if (Objects.isNull(walletInfo)) {
            walletInfo = walletInfoService.getWallet(userInfo.getId());
        }

        BigDecimal availableAmount = Objects.isNull(walletInfo) ? BigDecimal.ZERO : walletInfo.getAvailableAmount();
        userCashSignInfoResp.setAvailableAmount(availableAmount);
        return userCashSignInfoResp;
    }


    private void checkSignReq(UserSignReq userSignReq, UserInfo userInfo) {
        Assert.notNull(userSignReq, "??????????????????");
        Assert.notNull(userSignReq.getUserId(), "userId??????");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getBankCard()), "?????????????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getBankName()), "???????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getIdCard()), "?????????????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getMobile()), "???????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getName()), "???????????????");
        Assert.isTrue(Validator.isMobile(userSignReq.getMobile()), "????????????????????????");
        Assert.isTrue(Validator.isCitizenId(userSignReq.getIdCard()), "??????????????????");
        ValidateCardInfoResp validateCardInfoResp = alipayHttpClient.validateAndCacheCardInfo(userSignReq.getBankCard(), true);
        Assert.isTrue(validateCardInfoResp != null && validateCardInfoResp.getValidated(), "??????????????????");

        Assert.isTrue(Objects.equals(userInfo.getId(), userSignReq.getUserId()), "???????????????????????????????????????");
        Assert.isTrue(Objects.equals(userInfo.getMobile(), userSignReq.getMobile()), "????????????????????????????????????????????????");
    }

    private void checkSubmitReq(UserCashSubmitReq cashSubmitReq, UserInfo userInfo) {
        Assert.notNull(cashSubmitReq.getUserId(), "userId??????");
        Assert.isTrue(StringUtils.isNotEmpty(userInfo.getBankCard()), "?????????????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userInfo.getBankName()), "???????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userInfo.getIdCard()), "?????????????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userInfo.getMobile()), "???????????????");
        Assert.isTrue(StringUtils.isNotEmpty(userInfo.getName()), "???????????????");
        Assert.isTrue(cashSubmitReq.getAmount() != null && cashSubmitReq.getAmount().compareTo(BigDecimal.ZERO) > 0, "??????????????????");
        Assert.isTrue(Objects.equals(userInfo.getId(), cashSubmitReq.getUserId()), "???????????????????????????????????????");
        Assert.isTrue(Objects.equals(userInfo.getMobile(), cashSubmitReq.getMobile()), "????????????????????????????????????????????????");

        WalletInfo walletInfo = walletInfoService.getWallet(userInfo.getId());
        BigDecimal availableAmount = walletInfo.getAvailableAmount();
        Assert.isTrue(availableAmount.compareTo(cashSubmitReq.getAmount()) > -1, "???????????????????????????????????????");

        UserSignInfo userSignInfo = userSignInfoService.getByUserId(userInfo.getId());
        Assert.notNull(userSignInfo, "???????????????????????????????????????????????????");
        Integer signStatus = userSignInfo.getSignStatus();
        UserSignStatusEnum userSignStatusEnum = UserSignStatusEnum.getByCode(signStatus);
        switch (userSignStatusEnum) {
            case NOT_SIGN:
                throw new IllegalArgumentException("???????????????????????????????????????????????????");
            case SIGNING:
                throw new IllegalArgumentException("????????????????????????????????????");
            case FAILED:
                throw new IllegalArgumentException("?????????????????????????????????????????????????????????");
            default:
                break;
        }
    }

}
