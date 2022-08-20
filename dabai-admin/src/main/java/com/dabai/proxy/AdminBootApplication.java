package com.dabai.proxy;

import com.dabai.proxy.httpclient.spring.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: jinshan.zhu
 * @date: 2020/12/23 14:10
 */
@SpringBootApplication(scanBasePackages = "com.dabai.proxy")
@MapperScan(basePackages = "com.dabai.proxy.dao")
@EnableHttpClient(basePackages = "com.dabai.proxy.httpclient")
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableScheduling
public class AdminBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBootApplication.class, args);
    }
}
