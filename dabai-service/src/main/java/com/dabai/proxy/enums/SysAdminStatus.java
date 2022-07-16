package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 16:00
 */
@Getter
public enum SysAdminStatus {

    /**
     * 可用
     */
    NORMAL(1, "正常"),

    DISABLE(2, "禁用");

    private final Integer code;

    private final String desc;

    SysAdminStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SysAdminStatus getStatusByCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return Arrays.stream(SysAdminStatus.values()).filter(e -> e.getCode().equals(code)).
                findFirst().orElse(null);
    }
}
