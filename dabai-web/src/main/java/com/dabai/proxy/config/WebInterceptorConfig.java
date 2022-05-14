package com.dabai.proxy.config;

import com.dabai.proxy.config.token.TokenAuthInterceptor;
import com.dabai.proxy.lock.JdkLockFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/10 00:03
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenAuthInterceptor tokenAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenAuthInterceptor).addPathPatterns("/**");
    }

    @Bean
    public JdkLockFunction jdkLockFunction() {
        return new JdkLockFunction();
    }
}
