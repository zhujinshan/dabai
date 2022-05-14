package com.dabai.proxy.facade.impl;

import com.dabai.proxy.exception.HttpClientBusinessException;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberInfoResp;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.po.UserPlateformInfo;
import com.dabai.proxy.resp.UserInfoResp;
import com.dabai.proxy.service.UserInfoService;
import com.dabai.proxy.service.UserPlateformInfoService;
import com.dabai.proxy.service.WalletInfoService;
import lombok.extern.slf4j.Slf4j;
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
    public void saveUserPhone(String openId, String phone) {
        Assert.notNull(openId, "openId信息缺失");
        Assert.notNull(phone, "phoneNo信息缺失");
        Long userId = userInfoService.saveUserPhone(openId, phone);

        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(phone);
        HuanongResult<MemberInfoResp> result = huanongHttpClient.memberInfo(memberInfoParam);
        if (result == null || !Objects.equals(result.getState(), "200")) {
            log.error("华农会员信息交互异常, result:{}", result);
            throw new HttpClientBusinessException("华农会员信息交互异常");
        }

        MemberInfoResp data = result.getData();
        if (Objects.isNull(data)) {
            return;
        }
        userPlateformInfoService.save(userId, data);
        // 钱包开户
        walletInfoService.addWallet(userId);
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
        }
        return userInfoResp;
    }
}