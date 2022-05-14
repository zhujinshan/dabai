package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:48
 */
@Getter
public enum CashStatusEnum {

    INIT(0, "未提交"),
    SUCCESS(1, "成功提现"),
    FAILED(2, "提现失败"),
    CASHING(3, "提现中"),
    ;
    private final Integer code;
    private final String desc;

    CashStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CashStatusEnum getByCode(Integer code) {
        return Arrays.stream(CashStatusEnum.values()).filter(e -> Objects.equals(code, e.getCode()))
                .findFirst().orElse(null);
    }
}
