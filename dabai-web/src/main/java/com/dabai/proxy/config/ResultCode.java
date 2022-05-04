package com.dabai.proxy.config;

public enum ResultCode {

    SUCCESS(0, "成功"),

    FAILURE(-1, "失败"),

    PARAMETER_EXCEPTION(-2, "参数校验失败"),

    DATABASE_EXCEPTION(-3, "数据库异常"),

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