package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 16:00
 */
@Getter
public enum SysAdminRole {

    /**
     * 超级管理员
     */
    SUPPER_ADMIN(1, "超级管理员", 3),

    ADMIN(2, "管理员", 2),

    NORMAL_USER(3, "普通用户", 1);

    private final Integer code;

    private final String desc;

    private final Integer weight;

    SysAdminRole(Integer code, String desc, Integer weight) {
        this.code = code;
        this.desc = desc;
        this.weight = weight;
    }

    public static SysAdminRole getRoleByCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.stream(SysAdminRole.values()).filter(e -> e.getCode().equals(code)).
                findFirst().orElse(null);
    }
}
