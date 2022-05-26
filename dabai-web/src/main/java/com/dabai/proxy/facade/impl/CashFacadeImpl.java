package com.dabai.proxy.facade.impl;

import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.contants.DabaiContants;
import com.dabai.proxy.enums.CashStatusEnum;
import com.dabai.proxy.enums.UserSignStatusEnum;
import com.dabai.proxy.exception.HttpClientBusinessException;
import com.dabai.proxy.facade.CashFacade;
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

    @Value("${domain}")
    private String domain;

    @Value("${http.liness.transferCorpId}")
    private String transferCorpId;

    @Value("${http.liness.agreementType}")
    private String agreementType;

    @Override
    public UserCashSignInfoResp cashInfo(String openId) {
        Assert.notNull(openId, "openId缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        if (userInfo == null) {
            return null;
        }
        return buildSignInfoResp(userInfo, null, null);
    }

    @Override
    public UserCashSignInfoResp sign(String openId, UserSignReq userSignReq) {
        Assert.notNull(openId, "openId缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        Assert.notNull(userInfo, "未知用户信息，请重新登录");
        checkSignReq(userSignReq, userInfo);

        UserSignInfo userSignInfo = userSignInfoService.getByUserId(userInfo.getId());

        if (Objects.equals(userInfo.getBankCard(), userSignReq.getBankCard()) &&
                Objects.equals(userInfo.getName(), userSignReq.getName()) &&
                Objects.equals(userInfo.getIdCard(), userSignReq.getIdCard())) {
            if (userSignInfo != null && (userSignInfo.getSignStatus().equals(UserSignStatusEnum.SUCCESS.getCode())
                    || userSignInfo.getSignStatus().equals(UserSignStatusEnum.SIGNING.getCode()))) {
                log.info("已签约成功或签约中且信息无变化 不用二次签约。[userSignReq:{}]", userSignReq);
                return buildSignInfoResp(userInfo, UserSignStatusEnum.getByCode(userSignInfo.getSignStatus()), null);
            }
        }
        log.info("信息变化 需二次签约。[userSignReq:{}]", userSignReq);
        // 增加账户
        AddMerchantParam addMerchantParam = new AddMerchantParam();
        addMerchantParam.setCertificateType("ID");
        addMerchantParam.setCertificateNo(userSignReq.getIdCard());
        addMerchantParam.setName(userSignReq.getName());
        addMerchantParam.setMobileNo(userSignReq.getMobile());
        LinessBaseResult<AddMerchantResult> addMerchantResult = linessHttpClient.addMerchant(addMerchantParam);
        log.info("增加收款人 [addMerchantParam:{}, result={}]", addMerchantParam, addMerchantResult);
        if (addMerchantResult == null || !LinessHttpClient.SUCCESS_CODE.equals(addMerchantResult.getRetCode())) {
            throw new HttpClientBusinessException("增加收款人失败，请稍后重试");
        }
        // 签约
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
        log.info("签约 [signAgreementParam:{}, result={}]", signAgreementParam, signAgreement);

        if (Objects.isNull(signAgreement) || !LinessHttpClient.SUCCESS_CODE.equals(signAgreement.getRetCode())) {
            throw new HttpClientBusinessException("签约失败，请稍后重试");
        }

        //同步华保星身份证号等数据
        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(userSignReq.getMobile());
        memberInfoParam.setIdCardNo(userSignReq.getIdCard());
        memberInfoParam.setName(userSignReq.getName());
        try {
            HuanongResult<MemberInfoResp> result = huanongHttpClient.memberInfo(memberInfoParam);
            if (result == null || !Objects.equals(result.getState(), "200")) {
                log.error("华农会员信息交互异常, memberInfoParam:{}, result:{}", memberInfoParam, result);
            }
        } catch (Exception e) {
            log.error("华农会员信息交互异常, memberInfoParam:" + memberInfoParam, e);
        }

        SignAgreementResult retData = signAgreement.getRetData();
        CashFacadeImpl cashFacade = (CashFacadeImpl) AopContext.currentProxy();
        return cashFacade.saveSignInfo(userInfo.getId(), userSignReq, retData);
    }

    @Override
    public Result<String> cashSubmit(String openId, UserCashSubmitReq cashSubmitReq) {
        Assert.notNull(openId, "openId缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        Assert.notNull(userInfo, "未知用户信息，请重新登录");
        checkSubmitReq(cashSubmitReq, userInfo);
        String requestNo = String.valueOf(System.currentTimeMillis());

        TransferToBankCardParam transferToBankCardParam = new TransferToBankCardParam();
        transferToBankCardParam.setBankCardNo(cashSubmitReq.getBankCard());
        transferToBankCardParam.setName(cashSubmitReq.getName());
        transferToBankCardParam.setTransferCorpId(transferCorpId);
        transferToBankCardParam.setRequestNo(requestNo);
        transferToBankCardParam.setAmount(cashSubmitReq.getAmount().toString());
        transferToBankCardParam.setCertificateNo(cashSubmitReq.getIdCard());
        transferToBankCardParam.setNotifyUrl(domain + DabaiContants.ZX_CASH_CALL_BACK_URL);
        transferToBankCardParam.setRemark("用户佣金提现");

        CashSnapshot cashSnapshot = cashSnapshotService.init(userInfo.getId(), cashSubmitReq.getMobile(), transferToBankCardParam);
        LinessBaseResult<TransferToBankCardResult> transferToBankcard = linessHttpClient.transferToBankcard(transferToBankCardParam);
        log.info("提现 [transferToBankCardParam:{}, result={}]", transferToBankCardParam, transferToBankcard);

        if (Objects.isNull(transferToBankcard)) {
            throw new HttpClientBusinessException("提现失败，请稍后重试");
        }

        // 提现提交成功
        if (LinessHttpClient.SUCCESS_CODE.equals(transferToBankcard.getRetCode())) {
            TransferToBankCardResult retData = transferToBankcard.getRetData();
            cashSnapshot.setStatus(CashStatusEnum.CASHING.getCode());
            cashSnapshot.setDealNo(retData.getDealNo());
            cashSnapshot.setRemark(retData.getDealStatusMsg());
            walletInfoService.cashing(userInfo.getId(), cashSnapshot);
            return Result.success(retData.getDealNo());
        }
        // 提现提交异常
        String thirdResp = JsonUtils.toJson(transferToBankcard);
        String remark = transferToBankcard.getRetMsg();
        if ("T022".equals(transferToBankcard.getRetCode())) {
            remark = "可提现余额不足";
        }
        //这个是甲方特别要求的 :)
        if (transferToBankcard.getRetMsg().contains("月累计")) {
            remark = "每人当月最高可提现 9.7 万";
        }
        cashSnapshotService.cashFailed(cashSnapshot.getId(), remark, thirdResp);
        return Result.genResult(-1, remark, null);
    }

    @Override
    public CashInfoPageResult pageQuery(CashInfoPageReq cashInfoPageReq) {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        UserInfo userInfo = userInfoService.selectByOpenId(sessionInfo.getOpenId());
        tk.mybatis.mapper.util.Assert.notNull(userInfo, "用户无效，请重新登录");

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
        Assert.notNull(userSignReq, "签约信息缺失");
        Assert.notNull(userSignReq.getUserId(), "userId缺失");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getBankCard()), "银行卡信息缺失");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getBankName()), "开户行缺失");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getIdCard()), "身份证信息缺失");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getMobile()), "手机号缺失");
        Assert.isTrue(StringUtils.isNotEmpty(userSignReq.getName()), "用户名缺失");

        Assert.isTrue(Objects.equals(userInfo.getId(), userSignReq.getUserId()), "用户信息不匹配，请重新登录");
        Assert.isTrue(Objects.equals(userInfo.getMobile(), userSignReq.getMobile()), "手机号跟微信账号不一致，签约失败");
    }

    private void checkSubmitReq(UserCashSubmitReq cashSubmitReq, UserInfo userInfo) {
        Assert.notNull(cashSubmitReq.getUserId(), "userId缺失");
        Assert.isTrue(StringUtils.isNotEmpty(cashSubmitReq.getBankCard()), "银行卡信息缺失");
        Assert.isTrue(StringUtils.isNotEmpty(cashSubmitReq.getBankName()), "开户行缺失");
        Assert.isTrue(StringUtils.isNotEmpty(cashSubmitReq.getIdCard()), "身份证信息缺失");
        Assert.isTrue(StringUtils.isNotEmpty(cashSubmitReq.getMobile()), "手机号缺失");
        Assert.isTrue(StringUtils.isNotEmpty(cashSubmitReq.getName()), "用户名缺失");
        Assert.isTrue(cashSubmitReq.getAmount() != null && cashSubmitReq.getAmount().compareTo(BigDecimal.ZERO) > 0, "无效提现金额");
        Assert.isTrue(Objects.equals(userInfo.getId(), cashSubmitReq.getUserId()), "用户信息不匹配，请重新登录");
        Assert.isTrue(Objects.equals(userInfo.getMobile(), cashSubmitReq.getMobile()), "手机号跟微信账号不一致，提现失败");

        WalletInfo walletInfo = walletInfoService.getWallet(userInfo.getId());
        BigDecimal availableAmount = walletInfo.getAvailableAmount();
        Assert.isTrue(availableAmount.compareTo(cashSubmitReq.getAmount()) > -1, "提现金额超过全部可提现金额");
    }

}
