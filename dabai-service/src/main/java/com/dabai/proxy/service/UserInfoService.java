package com.dabai.proxy.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dabai.proxy.po.UserInfo;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 23:44
 */
public interface UserInfoService {

    /**
     * 保存微信用户
     *
     * @param wxMaUserInfo 微信信息
     */
    void saveUser(WxMaUserInfo wxMaUserInfo);

    /**
     * 保存微信手机
     *
     * @param openId      openId
     * @param phoneNo 手机信息
     */
    Long saveUserPhone(String openId, String phoneNo);


    /**
     * 通过openId查找用户
     * @param openId openId
     * @return 用户信息
     */
    UserInfo selectByOpenId(String openId);
}
