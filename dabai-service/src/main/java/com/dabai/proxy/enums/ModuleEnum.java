package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/7 20:40
 */
@Getter
public enum ModuleEnum {

    DATA_OVERVIEW(1, "数据概览"),

    MEMBER(2, "会员数据"),

    PERFORM(3, "业绩数据"),

    INCOME(4, "收支明细"),

    WATER_LIST(5, "流水清单"),

    ;

    private final Integer code;

    private final String desc;

    ModuleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ModuleEnum getByCode(Integer code) {
        if(code == null) {
            return null;
        }
        return Arrays.stream(ModuleEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }


}
