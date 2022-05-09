package com.dabai.proxy.httpclient.spring;

import com.dabai.proxy.httpclient.core.GlobalContext;
import com.dabai.proxy.httpclient.core.log.HttpMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zhujinshan
 */
@Configuration
public class HttpClientAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientAutoConfiguration.class);

    @Autowired(required = false)
    private HttpMonitor httpMonitor;

    @PostConstruct
    public void init() {
        if (httpMonitor != null) {
            GlobalContext.getInstance().setMonitor(httpMonitor);
        }
    }

    static class PostProcessorConfig {

        @Bean
        public static HttpClientBeanFactoryPostProcessor httpClientBeanFactoryPostProcessor() {
            logger.debug("init : HttpClientBeanFactoryPostProcessor");
            return new HttpClientBeanFactoryPostProcessor();
        }

    }
}
