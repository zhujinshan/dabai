package com.dabai.proxy.encypt.config;


import com.dabai.proxy.encypt.core.DecyptInterceptor;
import com.dabai.proxy.encypt.core.SignatureContext;
import com.dabai.proxy.encypt.core.SignatureVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 13:56
 */
@EnableConfigurationProperties(DecryptListConfig.class)
public class DecryptAutoConfig {

    @Autowired
    private DecryptListConfig decryptListConfig;

    @Bean
    @ConditionalOnProperty(name = "api-encrypt.enable-aes")
    public DecyptInterceptor decyptInterceptor() {
        return new DecyptInterceptor();
    }

    @Bean
    public FilterRegistrationBean<SignatureVerifyFilter> filterRegistration() {
        FilterRegistrationBean<SignatureVerifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SignatureVerifyFilter(decryptListConfig));
        registration.setName("SignatureVerifyFilter");
        if (decryptListConfig.getOrder() != null) {
            registration.setOrder(decryptListConfig.getOrder());
        }
        return registration;
    }

    @Bean
    public SignatureContext signatureContext() {
        return new SignatureContext(decryptListConfig);
    }
}
