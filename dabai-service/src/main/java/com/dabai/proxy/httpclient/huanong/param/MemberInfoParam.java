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

    /**
     * 邀请人会员编码
     */
    private String inviterNo;

    /**
     * 邀请人归属机构
     */
    private String inviterComCode;
}
