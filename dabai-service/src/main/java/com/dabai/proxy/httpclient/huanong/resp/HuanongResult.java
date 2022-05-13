package com.dabai.proxy.httpclient.huanong.resp;

import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 18:58
 */
@Data
public class HuanongResult<T> {
    /**
     * 200-成功 其他-失败
     */
    private String state;

    private String message;

    private T data;
}
