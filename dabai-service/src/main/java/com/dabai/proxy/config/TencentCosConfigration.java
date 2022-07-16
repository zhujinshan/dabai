package com.dabai.proxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 21:18
 */
@Configuration
@EnableConfigurationProperties(TencentCosConfig.class)
@ConditionalOnProperty(prefix = "tencentcloud", name = "cos")
public class TencentCosConfigration {

    @Autowired
    private TencentCosConfig tencentCosConfig;



}
