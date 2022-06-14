package com.dabai.proxy.facade.impl;

import com.dabai.proxy.exception.HttpClientBusinessException;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.po.WalletInfo;
import com.dabai.proxy.req.UserPlateformReq;
import com.dabai.proxy.resp.UserInfoResp;
import com.dabai.proxy.service.UserInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:16
 */
@Component
@Slf4j
public class UserInfoFacadeImpl implements UserInfoFacade {

    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private HuanongHttpClient huanongHttpClient;
    @Autowired
    private UserPlateformInfoService userPlateformInfoService;
    @Autowired
    private WalletInfoService walletInfoService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveUserPhone(String openId, String phone, Long parentId) {
        Assert.notNull(openId, "openId信息缺失");
        Assert.notNull(phone, "phoneNo信息缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        Assert.notNull(userInfo, "未知用户信息");
        if (StringUtils.isNotEmpty(userInfo.getMobile())) {
            log.info("该用户手机号不为空，跳过处理，userInfo: {} ", userInfo);
            return;
        }
        if (Objects.nonNull(userInfo.getParentUserId()) && userInfo.getParentUserId() > 0) {
            parentId = userInfo.getParentUserId();
        }
        userInfoService.saveUserPhone(userInfo.getId(), phone, parentId);
        UserPlateformInfo parentPlateform = null;

        if (Objects.nonNull(parentId) && parentId > 0) {
            parentPlateform = userPlateformInfoService.getByUserId(parentId);
        }

        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(phone);
        if (Objects.nonNull(parentPlateform)) {
            memberInfoParam.setInviterNo(parentPlateform.getCode());
            memberInfoParam.setInviterComCode(parentPlateform.getOrganizationCode());
        }
        HuanongResult<MemberInfoResp> result = huanongHttpClient.memberInfo(memberInfoParam);
        if (result == null || !Objects.equals(result.getState(), "200")) {
            log.error("华农会员信息交互异常, result:{}", result);
            throw new HttpClientBusinessException("华农会员信息交互异常");
        }

        MemberInfoResp data = result.getData();
        if (Objects.isNull(data)) {
            return;
        }
        userPlateformInfoService.save(userInfo.getId(), data);
        // 钱包开户
        walletInfoService.addWallet(userInfo.getId());
    }

    @Override
    public UserInfoResp getUserInfo(String openId) {
        Assert.notNull(openId, "openId信息缺失");
        UserInfo userInfo = userInfoService.selectByOpenId(openId);
        if (userInfo == null) {
            return null;
        }
        UserInfoResp userInfoResp = new UserInfoResp();
        BeanUtils.copyProperties(userInfo, userInfoResp);
        UserPlateformInfo plateformInfo = userPlateformInfoService.getByUserId(userInfo.getId());
        if (plateformInfo != null) {
            userInfoResp.setMemberNo(plateformInfo.getCode());
            userInfoResp.setIdentityTag(plateformInfo.getIdentityTag() != null ? plateformInfo.getIdentityTag().intValue() : 0);
        }
        WalletInfo walletInfo = walletInfoService.getWallet(userInfo.getId());
        if (walletInfo != null) {
            userInfoResp.setAvailableAmount(walletInfo.getAvailableAmount());
            userInfoResp.setCashedAmount(walletInfo.getCashedAmount());
            userInfoResp.setTotalAmount(walletInfo.getTotalAmount());
        }
        return userInfoResp;
    }

    @Override
    public void updateUserPlateformInfo(UserPlateformReq userPlateformReq) {
        UserPlateformInfo userPlateformInfo = userPlateformInfoService.getByHbxMemberNo(userPlateformReq.getCode());
        Assert.notNull(userPlateformInfo, "华保星编码不存在");

        userPlateformInfo.setCode(userPlateformReq.getCode());
        userPlateformInfo.setIdentityTag(Byte.valueOf(userPlateformReq.getIdentityTag()));
        userPlateformInfo.setOrganizationCode(userPlateformReq.getOrganizationCode());
        userPlateformInfoService.updateUserPalteformInfo(userPlateformInfo);

    }
}
