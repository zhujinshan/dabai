package com.dabai.proxy.controller;

import com.dabai.proxy.cache.LocalCache;
import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.JwtTools;
import com.dabai.proxy.httpclient.tencentcloud.TencentSmsClient;
import com.dabai.proxy.po.SysAdmin;
import com.dabai.proxy.req.SysAdminLoginReq;
import com.dabai.proxy.service.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/5 23:02
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags = "管理员接口")
@Slf4j
public class SysUserController {

    @Autowired
    private TencentSmsClient tencentSmsClient;
    @Autowired
    private SysAdminService sysAdminService;

    @GetMapping(value = "/sendSubmitCode")
    @ApiOperation(value = "发送短信验证码", httpMethod = "GET")
    public Result<Boolean> sendSubmitCode(@RequestParam @ApiParam(value = "手机号", required = true) String mobile) {
        log.info("下发短信验证码：mobile：{}", mobile);
        if (LocalCache.contains(mobile)) {
            return Result.genResult(-1, "验证码还在有效期，请不要频繁请求", false);
        }
        // todo: 更换短信模板
        tencentSmsClient.sendSubmitVerificationCode(mobile);
        return Result.success(true);
    }


    @PostMapping(value = "/login")
    @ApiOperation(value = "登录", httpMethod = "POST")
    public Result<String> login(@ApiParam(value = "登录入参", required = true) @RequestBody SysAdminLoginReq sysAdminLoginReq) {
        log.info("login：sysAdminLoginReq：{}", sysAdminLoginReq);
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminLoginReq.getMobile()), "手机号不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(sysAdminLoginReq.getCode()), "验证码不能为空");
        Assert.isTrue(LocalCache.checkCode(sysAdminLoginReq.getMobile(), sysAdminLoginReq.getCode()), "验证码无效，请重新获取");

        SysAdmin sysAdmin = sysAdminService.getByMobile(sysAdminLoginReq.getMobile());
        Assert.notNull(sysAdmin, "无效账号，请重新输入手机号");
        String token = JwtTools.generateToken(sysAdmin.getId());
        return Result.success(token);
    }



}
