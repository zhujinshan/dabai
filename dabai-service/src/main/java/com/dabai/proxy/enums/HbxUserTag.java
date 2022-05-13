package com.dabai.proxy.enums;

import lombok.Getter;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 00:38
 */
@Getter
public enum HbxUserTag {
    MEMBER(1, "会员"),
    AGENT(2, "代理人");

    private final Integer code;

    private final String desc;

    HbxUserTag(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
