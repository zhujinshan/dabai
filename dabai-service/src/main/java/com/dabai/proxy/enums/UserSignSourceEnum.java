package com.dabai.proxy.enums;

import lombok.Getter;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:35
 */
@Getter
public enum UserSignSourceEnum {

    FC("风驰"),

    ZX("众信"),

    ZXJK("众信健康");

    private final String desc;

    UserSignSourceEnum(String desc) {
        this.desc = desc;
    }
}
