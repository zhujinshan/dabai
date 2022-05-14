package com.dabai.proxy.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 21:55
 */
@Configuration
public class TecentCloudClientConfig {

    @Autowired
    TencentCloudConfig tencentCloudConfig;

    @Bean
    public SmsClient smsClient() {
        Credential cred = new Credential(tencentCloudConfig.getSecretId(), tencentCloudConfig.getSecretKey());
        ClientProfile clientProfile = new ClientProfile();
        /* 实例化要请求产品(以sms为例)的client对象
         * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
        return new SmsClient(cred, "ap-guangzhou", clientProfile);
    }

}
