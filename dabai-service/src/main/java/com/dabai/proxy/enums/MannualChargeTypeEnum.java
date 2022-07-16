package com.dabai.proxy.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 10:45
 */
@Getter
public enum MannualChargeTypeEnum {

    A("展业费"),
    B("运营激励费"),
    C("基本法费"),
    ;
    private final String desc;

    MannualChargeTypeEnum(String desc) {
        this.desc = desc;
    }

    public static MannualChargeTypeEnum getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return Arrays.stream(MannualChargeTypeEnum.values()).filter(e -> Objects.equals(name, e.name()))
                .findFirst().orElse(null);
    }
}
