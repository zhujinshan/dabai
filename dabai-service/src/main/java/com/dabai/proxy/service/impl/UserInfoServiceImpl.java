package com.dabai.proxy.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dabai.proxy.dao.UserInfoMapper;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 23:47
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveUser(WxMaUserInfo wxMaUserInfo) {
        Assert.notNull(wxMaUserInfo, "微信信息缺失");
        Assert.isTrue(StringUtils.isNotEmpty(wxMaUserInfo.getOpenId()), "openid缺失");
        UserInfo userInfo = selectByOpenId(wxMaUserInfo.getOpenId());
        UserInfo currentUserInfo = buildFormWxUser(wxMaUserInfo);
        if (userInfo != null) {
            currentUserInfo.setId(userInfo.getId());
            userInfoMapper.updateByPrimaryKeySelective(currentUserInfo);
            return;
        }
        currentUserInfo.setCtime(new Date());
        userInfoMapper.insertSelective(currentUserInfo);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveUserPhone(Long userId, String phoneNo, Long parentId) {
        Assert.notNull(userId, "openId信息缺失");
        Assert.notNull(phoneNo, "phoneNo信息缺失");
        UserInfo updateUser = new UserInfo();
        updateUser.setMobile(phoneNo);
        updateUser.setId(userId);
        if (Objects.nonNull(parentId) && parentId > 0) {
            updateUser.setParentUserId(parentId);
        }
        userInfoMapper.updateByPrimaryKeySelective(updateUser);
    }

    @Override
    public UserInfo selectByOpenId(String openId) {
        Assert.isTrue(StringUtils.isNotEmpty(openId), "openId缺失");
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("openId", openId);
        return userInfoMapper.selectOneByExample(example);
    }

    @Override
    public UserInfo selectById(Long id) {
        Assert.notNull(id, "id缺失");
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public long saveSignInfo(Long id, String bankName, String bankCard,
                             String idCard, String name, String mobile) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName(name);
        userInfo.setBankCard(bankCard);
        userInfo.setBankName(bankName);
        userInfo.setIdCard(idCard);
        userInfo.setMobile(mobile);
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }


    private UserInfo buildFormWxUser(WxMaUserInfo wxMaUserInfo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(wxMaUserInfo, userInfo);
        userInfo.setUtime(new Date());
        userInfo.setValid(1);
        return userInfo;
    }
}
