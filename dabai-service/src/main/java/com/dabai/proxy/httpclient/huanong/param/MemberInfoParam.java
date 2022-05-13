package com.dabai.proxy.httpclient.huanong.param;

import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 18:48
 */
@Data
public class MemberInfoParam extends HuanongCommonParam{

    private String phone;

    private String idCardNo;

    private String name;
}
