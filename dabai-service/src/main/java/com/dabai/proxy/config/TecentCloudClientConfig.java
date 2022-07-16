package com.dabai.proxy.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 21:55
 */
@Configuration
public class TecentCloudClientConfig {

    @Autowired
    private TencentCloudConfig tencentCloudConfig;

    @Autowired
    private TencentCosConfig tencentCosConfig;

    @Bean
    public SmsClient smsClient() {
        Credential cred = new Credential(tencentCloudConfig.getSecretId(), tencentCloudConfig.getSecretKey());
        ClientProfile clientProfile = new ClientProfile();
        /* 实例化要请求产品(以sms为例)的client对象
         * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
        return new SmsClient(cred, "ap-guangzhou", clientProfile);
    }

    @Bean
    @ConditionalOnProperty(prefix = "tencentcloud", name = "cos")
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }

}
