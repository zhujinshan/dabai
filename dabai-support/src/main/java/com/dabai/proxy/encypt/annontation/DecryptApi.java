package com.dabai.proxy.encypt.annontation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: jinshan.zhu
 * @date: 2020/9/21 11:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DecryptApi {

    /**
     * 需要aes参数加密的appId
     */
    String[] aesAppId() default {};
}
