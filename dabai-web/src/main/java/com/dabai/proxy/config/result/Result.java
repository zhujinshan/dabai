package com.dabai.proxy.config.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    /**
     * 成功响应结果
     *
     * @param data 内容
     * @return 响应结果
     */
    public static <T> Result<T> genResult(final Integer code, final String msg, final T data) {
        return new Result<T>(code, msg, data);
    }

    public static <T> Result<T> success(final T data) {
        return new Result<T>(ResultCode.SUCCESS.getValue(), ResultCode.SUCCESS.getReason(), data);
    }
}
