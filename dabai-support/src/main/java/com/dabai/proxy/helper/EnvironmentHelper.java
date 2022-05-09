package com.dabai.proxy.helper;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/8 16:09
 */
@Component
public class EnvironmentHelper implements EnvironmentAware {

    public static Environment environment;

    @Override
    public void setEnvironment(Environment env) {
        environment = env;
    }
}
