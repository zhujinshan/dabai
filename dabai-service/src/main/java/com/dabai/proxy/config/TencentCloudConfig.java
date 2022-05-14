package com.dabai.proxy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Create by Ranzd on 2021/7/17 下午10:02
 *
 * @author ranzhendong@maoyan.com
 */

@Configuration
@ConfigurationProperties(prefix = "tencentcloud")
public class TencentCloudConfig {
    private String secretId;
    private String secretKey;
    private String submitCodeTemplateId;
    private String notifyTemplateId;
    private String loginTemplateId;
    private String registerTemplateId;
    private String rePasswordTemplateId;
    private String sdkAppId;
    private String signName;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSubmitCodeTemplateId() {
        return submitCodeTemplateId;
    }

    public void setSubmitCodeTemplateId(String submitCodeTemplateId) {
        this.submitCodeTemplateId = submitCodeTemplateId;
    }

    public String getLoginTemplateId() {
        return loginTemplateId;
    }

    public void setLoginTemplateId(String loginTemplateId) {
        this.loginTemplateId = loginTemplateId;
    }

    public String getRegisterTemplateId() {
        return registerTemplateId;
    }

    public void setRegisterTemplateId(String registerTemplateId) {
        this.registerTemplateId = registerTemplateId;
    }

    public String getNotifyTemplateId() {
        return notifyTemplateId;
    }

    public void setNotifyTemplateId(String notifyTemplateId) {
        this.notifyTemplateId = notifyTemplateId;
    }

    public String getSdkAppId() {
        return sdkAppId;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRePasswordTemplateId() {
        return rePasswordTemplateId;
    }

    public void setRePasswordTemplateId(String rePasswordTemplateId) {
        this.rePasswordTemplateId = rePasswordTemplateId;
    }
}
