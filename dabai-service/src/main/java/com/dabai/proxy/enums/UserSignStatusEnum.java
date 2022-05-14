package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:48
 */
@Getter
public enum UserSignStatusEnum {

    NOT_SIGN(0, "未签约"),
    SUCCESS(1, "已签约"),
    FAILED(2, "签约失败"),
    SIGNING(3, "签约中"),
    ;
    private final Integer code;
    private final String desc;

    UserSignStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserSignStatusEnum getByCode(Integer code) {
        return Arrays.stream(UserSignStatusEnum.values()).filter(e -> Objects.equals(code, e.getCode()))
                .findFirst().orElse(null);
    }
}
