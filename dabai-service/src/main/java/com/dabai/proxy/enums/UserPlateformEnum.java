package com.dabai.proxy.enums;

import lombok.Getter;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:35
 */
@Getter
public enum UserPlateformEnum {

    HBX("华保星");

    private final String desc;

    UserPlateformEnum(String desc) {
        this.desc = desc;
    }
}
