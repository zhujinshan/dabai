package com.dabai.proxy.httpclient.huanong.resp;

import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 19:04
 */
@Data
public class MemberInfoResp {

    private String phone;

    private String memberNo;

    //1：是 0否
    private String isAgent;

    private String comCode;
}
