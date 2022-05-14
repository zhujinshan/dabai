package com.dabai.proxy.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 10:45
 */
@Getter
public enum WalletFlowTypeEnum {

    ADD(1, "收入"),
    WITHDRAWAL(2, "提现"),
    REFUND(3, "退款"),
    ;
    private final Integer code;
    private final String desc;

    WalletFlowTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WalletFlowTypeEnum getByCode(Integer code) {
        return Arrays.stream(WalletFlowTypeEnum.values()).filter(e -> Objects.equals(code, e.getCode()))
                .findFirst().orElse(null);
    }
}
