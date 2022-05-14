package com.dabai.proxy.enums;

import lombok.Getter;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:35
 */
@Getter
public enum SignBusinessSourceEnum {

    RFT("融富通");

    private final String desc;

    SignBusinessSourceEnum(String desc) {
        this.desc = desc;
    }
}
