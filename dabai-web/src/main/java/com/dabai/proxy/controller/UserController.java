package com.dabai.proxy.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.config.token.JwtTools;
import com.dabai.proxy.config.wx.WxMaConfiguration;
import com.dabai.proxy.config.wx.WxMaProperties;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.resp.UserInfoResp;
import com.dabai.proxy.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.Assert;

@RestController
@RequestMapping("/user")
@Api(tags = "微信会员接口")
@Slf4j
public class UserController {

    @Autowired
    private WxMaProperties wxMaProperties;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoFacade userInfoFacade;

    /**
     * 登陆接口
     */
    @GetMapping("/wxlogin")
    @ApiOperation(value = "微信授权登录", httpMethod = "GET")
    public Result<String> login(@ApiParam("code") @RequestParam(value = "code") String code) throws WxErrorException {
        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
        log.info("sessionKey: {}, openid: {}", session.getSessionKey(), session.getOpenid());
        return Result.success(JwtTools.generateToken(session.getSessionKey(), session.getOpenid()));
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/wxInfo")
    @CheckToken
    @ApiOperation(value = "同步微信用户基本信息", httpMethod = "GET")
    public Result<Boolean> info(@ApiParam("signature") @RequestParam(value = "signature") String signature, @ApiParam("rawData") @RequestParam(value = "rawData") String rawData,
                                @ApiParam("encryptedData") @RequestParam(value = "encryptedData") String encryptedData,
                                @ApiParam("iv") @RequestParam(value = "iv") String iv) {
        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        // 用户信息校验
        Assert.isTrue(wxService.getUserService().checkUserInfo(sessionInfo.getSessionKey(), rawData, signature), "签名验证失败");
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionInfo.getSessionKey(), encryptedData, iv);
        userInfoService.saveUser(userInfo);
        return Result.success(Boolean.TRUE);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/wxPhone")
    @CheckToken
    @ApiOperation(value = "同步微信手机号", httpMethod = "GET")
    public Result<Boolean> phone(@ApiParam("signature") @RequestParam(value = "signature") String signature,
                                 @ApiParam("rawData") @RequestParam(value = "rawData") String rawData,
                                 @ApiParam("encryptedData") @RequestParam(value = "encryptedData") String encryptedData,
                                 @ApiParam("iv") @RequestParam(value = "iv") String iv) {
        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        // 用户信息校验
        Assert.isTrue(wxService.getUserService().checkUserInfo(sessionInfo.getSessionKey(), rawData, signature), "签名验证失败");
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionInfo.getSessionKey(), encryptedData, iv);
        userInfoFacade.saveUserPhone(sessionInfo.getOpenId(), phoneNoInfo.getPhoneNumber());
        return Result.success(Boolean.TRUE);
    }


    @GetMapping("/getInfo")
    @CheckToken
    @ApiOperation(value = "获取用户信息", httpMethod = "GET")
    public Result<UserInfoResp> userInfo() {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        return Result.success(userInfoFacade.getUserInfo(sessionInfo.getOpenId()));
    }


}
