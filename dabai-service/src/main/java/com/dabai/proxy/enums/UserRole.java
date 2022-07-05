package com.dabai.proxy.enums;

import lombok.Getter;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/3 15:51
 */
@Getter
public enum UserRole {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(3),

    /**
     * 管理员
     */
    ADMIN(2),

    /**
     * 普通后台会员
     */
    NORMAL(1);

    private final Integer weight;

    UserRole(Integer weight) {
        this.weight = weight;
    }
}
