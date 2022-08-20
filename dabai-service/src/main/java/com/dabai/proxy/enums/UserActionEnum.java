package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/20 19:18
 */
@Getter
public enum UserActionEnum {
    VISIT(1, "访问"),

    INVITE(2, "邀请"),
    ;

    private final Integer code;

    private final String desc;

    UserActionEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserActionEnum getByCode(Integer code) {
        if(code == null) {
            return null;
        }
        return Arrays.stream(UserActionEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }
}
