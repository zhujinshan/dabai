package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.config.security.PathRole;
import com.dabai.proxy.enums.SysAdminRole;
import com.dabai.proxy.httpclient.tencentcloud.TencentCosClient;
import com.dabai.proxy.resp.CosCredentialsResp;
import com.tencent.cloud.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 23:00
 */
@RestController
@RequestMapping("/cos")
@Api(tags = "腾讯cos")
@Slf4j
public class TencentCosController {

    @Resource
    private TencentCosClient tecentCosClient;

    @GetMapping(value = "/getCredential")
    @ApiOperation(value = "获取临时凭证", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<CosCredentialsResp> getCredential() {
        Response credential = tecentCosClient.getCredential();
        CosCredentialsResp cosCredentialsResp = new CosCredentialsResp();
        cosCredentialsResp.setTmpSecretId(credential.credentials.tmpSecretId);
        cosCredentialsResp.setSessionToken(credential.credentials.sessionToken);
        cosCredentialsResp.setTmpSecretKey(credential.credentials.tmpSecretKey);
        cosCredentialsResp.setToken(credential.credentials.token);
        return Result.success(cosCredentialsResp);
    }

    @GetMapping(value = "/presignedUrl")
    @ApiOperation(value = "预览图片", httpMethod = "GET")
    @PathRole(role = SysAdminRole.NORMAL_USER)
    public Result<String> presignedUrl(@RequestParam @ApiParam(value = "图片key", required = true) String key) {
        URL url = tecentCosClient.generatePresignedUrl(key);
        return Result.success(url.toString());
    }
}
