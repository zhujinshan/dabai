package com.dabai.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: jinshan.zhu
 * @date: 2020/12/23 14:10
 */
@SpringBootApplication(scanBasePackages = "com.dabai.proxy")
@MapperScan(basePackages = "com.dabai.proxy.dao")
public class WebBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBootApplication.class, args);
    }
}
