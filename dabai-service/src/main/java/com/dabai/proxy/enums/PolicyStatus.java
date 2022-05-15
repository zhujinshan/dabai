package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:15
 */
@Getter
public enum PolicyStatus {

    REFUND(0, "已退款"),
    COMPLETE(1, "已完成"),
    ;
    private final Integer code;
    private final String desc;

    PolicyStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PolicyStatus getByCode(Integer code) {
        return Arrays.stream(PolicyStatus.values()).filter(e -> Objects.equals(code, e.getCode()))
                .findFirst().orElse(null);
    }
}
