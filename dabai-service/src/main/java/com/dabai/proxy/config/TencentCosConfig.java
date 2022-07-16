package com.dabai.proxy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
 * @author: jinshan.zhu
 * @date: 2022/7/16 21:16
 */
@Configuration
@ConfigurationProperties(prefix = "tencentcloud.cos")
@Data
public class TencentCosConfig {

    private String secretId;

    private String secretKey;

    private String bucket;
}
