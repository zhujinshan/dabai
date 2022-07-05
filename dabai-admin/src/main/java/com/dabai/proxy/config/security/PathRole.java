package com.dabai.proxy.config.security;

import com.dabai.proxy.enums.UserRole;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/3 15:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PathRole {

    UserRole role() default UserRole.NORMAL;

    boolean needLogin() default true;
}
