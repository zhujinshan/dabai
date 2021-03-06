package com.dabai.proxy.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.excel.EasyExcel;
import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.UserSessionContext;
import com.dabai.proxy.config.UserSessionInfo;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.result.ResultCode;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.config.token.JwtTools;
import com.dabai.proxy.config.wx.WxMaConfiguration;
import com.dabai.proxy.config.wx.WxMaProperties;
import com.dabai.proxy.facade.UserInfoFacade;
import com.dabai.proxy.httpclient.huanong.HuanongHttpClient;
import com.dabai.proxy.httpclient.huanong.param.MemberInfoParam;
import com.dabai.proxy.httpclient.huanong.resp.HuanongResult;
import com.dabai.proxy.httpclient.huanong.resp.MemberForwardStarResp;
import com.dabai.proxy.httpclient.tencentcloud.TencentSmsClient;
import com.dabai.proxy.po.UserInfo;
import com.dabai.proxy.req.WxPhoneInfoReq;
import com.dabai.proxy.req.WxUserInfoReq;
import com.dabai.proxy.resp.MemberInfoExport;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(tags = "????????????")
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
     * ????????????
     */
    @GetMapping("/wxlogin")
    @ApiOperation(value = "????????????????????????????????????token?????????header???access_token", httpMethod = "GET")
    public Result<String> login(@ApiParam(value = "code", required = true) @RequestParam(value = "code") String code) throws WxErrorException {
        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
        log.info("sessionKey: {}, openid: {}", session.getSessionKey(), session.getOpenid());
        return Result.success(JwtTools.generateToken(session.getSessionKey(), session.getOpenid()));
    }

    /**
     * <pre>
     * ????????????????????????
     * </pre>
     */
    @PostMapping("/wxInfo")
    @CheckToken
    @ApiOperation(value = "??????????????????????????????", httpMethod = "POST")
    public Result<Boolean> info(@RequestBody WxUserInfoReq wxUserInfoReq) {
        log.info("??????????????????????????????, wxUserInfoReq: {}", wxUserInfoReq);
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getSignature()), "signature is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getRawData()), "rawData is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getEncryptedData()), "encryptedData is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getIv()), "iv is required");

        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        // ??????????????????
        Assert.isTrue(wxService.getUserService().checkUserInfo(sessionInfo.getSessionKey(), wxUserInfoReq.getRawData(), wxUserInfoReq.getSignature()), "??????????????????");
        // ??????????????????
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionInfo.getSessionKey(), wxUserInfoReq.getEncryptedData(), wxUserInfoReq.getIv());
        if (StringUtils.isEmpty(userInfo.getOpenId())) {
            userInfo.setOpenId(sessionInfo.getOpenId());
        }
        log.info("??????????????????????????????, userInfo:{}", userInfo);
        userInfoService.saveUser(userInfo);
        return Result.success(Boolean.TRUE);
    }

    /**
     * <pre>
     * ?????????????????????????????????
     * </pre>
     */
    @PostMapping("/wxPhone")
    @CheckToken
    @ApiOperation(value = "?????????????????????", httpMethod = "POST")
    public Result<Boolean> phone(@RequestBody WxPhoneInfoReq wxUserInfoReq) {
        log.info("?????????????????????, wxUserInfoReq: {}", wxUserInfoReq);
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getEncryptedData()), "encryptedData is required");
        Assert.isTrue(StringUtils.isNotEmpty(wxUserInfoReq.getIv()), "iv is required");

        String appid = wxMaProperties.getConfigs().get(0).getAppid();
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        // ??????????????????
        // Assert.isTrue(wxService.getUserService().checkUserInfo(sessionInfo.getSessionKey(), rawData, signature), "??????????????????");
        // ??????
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionInfo.getSessionKey(), wxUserInfoReq.getEncryptedData(), wxUserInfoReq.getIv());
        log.info("?????????????????????, phoneNoInfo:{}", phoneNoInfo);
        userInfoFacade.saveUserPhone(sessionInfo.getOpenId(), phoneNoInfo.getPhoneNumber(), wxUserInfoReq.getParentId());
        return Result.success(Boolean.TRUE);
    }

    @GetMapping("/getInfo")
    @CheckToken
    @ApiOperation(value = "??????????????????", httpMethod = "GET")
    public Result<UserInfoResp> userInfo() {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        return Result.success(userInfoFacade.getUserInfo(sessionInfo.getOpenId()));
    }


    @GetMapping(value = "/sendSubmitCode")
    @CheckToken
    @ApiOperation(value = "???????????????????????????", httpMethod = "GET")
    public Result<Boolean> sendSubmitCode(@RequestParam @ApiParam(value = "?????????", required = true) String mobile) {
        log.info("????????????????????????mobile???{}", mobile);
        if (LocalCache.contains(mobile)) {
            return Result.genResult(-1, "????????????????????????????????????????????????", false);
        }
        tencentSmsClient.sendSubmitVerificationCode(mobile);
        return Result.success(true);
    }

    @GetMapping(value = "/getBanner")
    @CheckToken
    @ApiOperation(value = "??????banner", httpMethod = "GET")
    public Result<MemberForwardStarResp> getBanner() {
        UserSessionInfo sessionInfo = UserSessionContext.getSessionInfo();
        UserInfo userInfo = userInfoService.selectByOpenId(sessionInfo.getOpenId());
        Assert.notNull(userInfo, "????????????");

        MemberInfoParam memberInfoParam = new MemberInfoParam();
        memberInfoParam.setPhone(userInfo.getMobile());

        HuanongResult<MemberForwardStarResp> result = huanongHttpClient.forwardStarMini(memberInfoParam);
        if (result == null) {
            log.error("??????banner????????????, result is null");
            return Result.genResult(ResultCode.FAILURE.getValue(), "????????????????????????", null);
        }

        if (!Objects.equals(result.getState(), "200")) {
            log.error("??????banner????????????, result:{}", result);
            return Result.genResult(ResultCode.FAILURE.getValue(), result.getMessage(), null);
        }

        return Result.success(result.getData());
    }

    @GetMapping(value = "/export/zwzzwx666")
    public void export(HttpServletResponse response) {
        response.addHeader("Content-Disposition", "attachment;filename=" + "member-" + System.currentTimeMillis() + ".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            List<MemberInfoExport> memberInfoExports = userInfoFacade.getAllUserInfo();
            EasyExcel.write(outputStream, MemberInfoExport.class).sheet("????????????").doWrite(memberInfoExports);
        } catch (Exception e) {
            log.error("export error", e);
        }
    }

}
