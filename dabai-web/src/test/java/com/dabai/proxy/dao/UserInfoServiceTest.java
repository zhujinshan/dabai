package com.dabai.proxy.dao;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dabai.proxy.BaseTest;
import com.dabai.proxy.service.UserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/21 20:46
 */
public class UserInfoServiceTest extends BaseTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testSaveUser() {
        //userInfo:WxMaUserInfo(openId=o73o15ewCqKxlmkYh0Jm5v5gm4Dk, nickName=微信用户, gender=0, language=, city=, province=, country=, avatarUrl=https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132, unionId=null, watermark=Watermark(timestamp=1653137037, appid=wxeeff2ac6fe1b54bc))
        WxMaUserInfo wxMaUserInfo = new WxMaUserInfo();
        wxMaUserInfo.setOpenId("o73o15ewCqKxlmkYh0Jm5v5gm4Dk");
        wxMaUserInfo.setNickName("微信用户");
        wxMaUserInfo.setGender("0");
        wxMaUserInfo.setAvatarUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132");
        userInfoService.saveUser(wxMaUserInfo);
    }
}
