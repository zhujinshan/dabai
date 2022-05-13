package com.dabai.proxy.httpclient.huanong.param;

import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 18:46
 */
@Data
public class HuanongCommonParam {

    private String appid;

    private String nonceStr;

    private String timestamp;

    private String sign;
}
