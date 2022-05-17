package com.dabai.proxy.controller;

import com.dabai.proxy.config.result.Result;
import com.dabai.proxy.dto.PolicyCallBackDto;
import com.dabai.proxy.dto.PolicyInfoDto;
import com.dabai.proxy.facade.PolicyInfoFacade;
import com.dabai.proxy.utils.EncryptUtils;
import com.dabai.proxy.utils.JsonUtils;
import com.dabai.proxy.utils.RSAUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * 保单
 *
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:52
 */
@RestController
@RequestMapping("/policy")
@Api(tags = "保单回调接口")
@Slf4j
public class PolicyCallbackController {

    @Value("${http.huanong.privateKey}")
    private String privateKey;

    @Resource
    private PolicyInfoFacade policyInfoFacade;

    @PostMapping(value = "/callback")
    public Result<Boolean> callback(@RequestHeader("SecurityKey") String securityKey, @RequestBody String param) throws Exception {
        log.info("保单回传,原始密钥: {}", securityKey);
        log.info("保单回传,原始报文: {}", param);

        String AesKey = RSAUtil.decryptByPrivateKey(privateKey, securityKey);
        log.info("保单回传,解密后的密钥: {}", AesKey);

        byte[] decrypt = EncryptUtils.decrypt(Base64.getUrlDecoder().decode(param), AesKey);
        String content = new String(decrypt, StandardCharsets.UTF_8);
        log.info("保单回传,解密后的报文: {}", content);

        PolicyCallBackDto policyCallBack = JsonUtils.parse(content, PolicyCallBackDto.class);
        if (policyCallBack != null) {
            List<PolicyInfoDto> data = policyCallBack.getData();
            if (!CollectionUtils.isEmpty(data)) {
                for (PolicyInfoDto policyInfoDto : data) {
                    if (policyInfoDto != null && policyInfoDto.getStatus() != null) {
                        Integer status = policyInfoDto.getStatus();
                        switch (status) {
                            case 0:
                                policyInfoFacade.policyRefund(policyInfoDto);
                                break;
                            case 1:
                                policyInfoFacade.policyComplete(policyInfoDto);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        return Result.success(true);
    }
}
