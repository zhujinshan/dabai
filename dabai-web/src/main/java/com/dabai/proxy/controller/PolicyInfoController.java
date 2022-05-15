package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.token.CheckToken;
import com.dabai.proxy.req.PolicyInfoPageReq;
import com.dabai.proxy.resp.PolicyInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:07
 */
@RestController
@RequestMapping("/policyInfo")
@Api(tags = "保单查询接口")
@Slf4j
public class PolicyInfoController {

    @PostMapping("/pageQuery")
    @CheckToken
    @ApiOperation(value = "同步微信用户基本信息", httpMethod = "POST")
    public Result<PolicyInfoResp> pageQuery(@RequestBody PolicyInfoPageReq policyInfoPageReq) {
        return null;
    }
}
