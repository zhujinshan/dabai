package com.dabai.proxy.config.result;

public enum ResultCode {

    SUCCESS(0, "成功"),

    FAILURE(-1, "系统异常"),

    UN_LOGIN(-2, "未登录"),

    PARAMETER_EXCEPTION(-3, "参数异常"),

    CAN_NOT_VISIT(-4, "无权访问"),
    ;

    private final Integer value;

    private final String reason;

    ResultCode(final Integer value, final String reason) {
        this.value = value;
        this.reason = reason;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getReason() {
        return this.reason;
    }

}