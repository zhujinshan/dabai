package com.dabai.proxy.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.config.token.JwtTools;
import com.dabai.proxy.config.wx.WxMaConfiguration;
import com.dabai.proxy.config.wx.WxMaProperties;
import com.dabai.proxy.exception.HttpClientBusinessException;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberForwardStarResp;
import com.dabai.proxy.httpclient.tencentcloud.TencentSmsClient;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.req.WxPhoneInfoReq;
import com.dabai.proxy.req.WxUserInfoReq;
import com.dabai.proxy.resp.UserInfoResp;
import com.dabai.proxy.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(tags = "会员接口")
@Slf4j
public class UserController {

    @Autowired
    private WxMaProperties wxMaProperties;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoFacade userInfoFacade;

    @Autowired
    private TencentSmsClient tencentSmsClient;

    @Resource
    private HuanongHttpClient huanongHttpClient;

    /**
     * 登陆接口
     */
    @GetMapping("/wxlogin")
    @ApiOperation(value = "微信授权登录，返回自定义token，存入header头access_token", httpMethod = "GET")
    public Result<String> login(@ApiParam(value = "code", required = true) @RequestParam(value = "code") String code) throws WxErrorException {
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
    @PostMapping("/wxInfo")
    @CheckToken
    @ApiOperation(value = "同步微信用户基本信息", httpMethod = "POST")
    public Result<Boolean> info(@RequestBody WxUserInfoReq wxUserInfoReq) {
        log.info("同步微信用户基本信息, wxUserInfoReq: {}", wxUserInfoReq);
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getSignature()), "signature is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getRawData()), "rawData is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getEncryptedData()), "encryptedData is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getIv()), "iv is required");

        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        // 用户信息校验
        Assert.isTrue(wxService.getUserService().checkUserInfo(sessionInfo.getSessionKey(), wxUserInfoReq.getRawData(), wxUserInfoReq.getSignature()), "签名验证失败");
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionInfo.getSessionKey(), wxUserInfoReq.getEncryptedData(), wxUserInfoReq.getIv());
        if (StringUtils.isEmpty(userInfo.getOpenId())) {
            userInfo.setOpenId(sessionInfo.getOpenId());
        }
        log.info("同步微信用户基本信息, userInfo:{}", userInfo);
        userInfoService.saveUser(userInfo);
        return Result.success(Boolean.TRUE);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @PostMapping("/wxPhone")
    @CheckToken
    @ApiOperation(value = "同步微信手机号", httpMethod = "POST")
    public Result<Boolean> phone(@RequestBody WxPhoneInfoReq wxUserInfoReq) {
        log.info("同步微信手机号, wxUserInfoReq: {}", wxUserInfoReq);
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getEncryptedData()), "encryptedData is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getIv()), "iv is required");

        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        // 用户信息校验
        // Assert.isTrue(wxService.getUserService().checkUserInfo(sessionInfo.getSessionKey(), rawData, signature), "签名验证失败");
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionInfo.getSessionKey(), wxUserInfoReq.getEncryptedData(), wxUserInfoReq.getIv());
        log.info("同步微信手机号, phoneNoInfo:{}", phoneNoInfo);
        userInfoFacade.saveUserPhone(sessionInfo.getOpenId(), phoneNoInfo.getPhoneNumber(), wxUserInfoReq.getParentId());
        return Result.success(Boolean.TRUE);
    }

    @GetMapping("/getInfo")
    @CheckToken
    @ApiOperation(value = "获取用户信息", httpMethod = "GET")
    public Result<UserInfoResp> userInfo() {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        return Result.success(userInfoFacade.getUserInfo(sessionInfo.getOpenId()));
    }


    @GetMapping(value = "/sendSubmitCode")
    @CheckToken
    @ApiOperation(value = "提现发送短信验证码", httpMethod = "GET")
    public Result<Boolean> sendSubmitCode(@RequestParam @ApiParam(value = "手机号", required = true) String mobile) {
        log.info("下发短信验证码：mobile：{}", mobile);
        if (LocalCache.contains(mobile)) {
            return Result.genResult(-1, "验证码还在有效期，请不要频繁请求", false);
        }
        tencentSmsClient.sendSubmitVerificationCode(mobile);
        return Result.success(true);
    }

    @GetMapping(value = "/getBanner")
    @CheckToken
    @ApiOperation(value = "获取banner列表", httpMethod = "GET")
    public Result<MemberForwardStarResp> getBanner() {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        UserInfo userInfo = userInfoService.selectByOpenId(sessionInfo.getOpenId());
        Assert.notNull(userInfo, "未知用户");

        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(userInfo.getMobile());

        HuanongResult<MemberForwardStarResp> result = huanongHttpClient.forwardStarMini(memberInfoParam);
        if (result == null || !Objects.equals(result.getState(), "200")) {
            log.error("华农banner交互异常, result:{}", result);
            throw new HttpClientBusinessException("华农会员信息交互异常");
        }

        return Result.success(result.getData());
    }

}
