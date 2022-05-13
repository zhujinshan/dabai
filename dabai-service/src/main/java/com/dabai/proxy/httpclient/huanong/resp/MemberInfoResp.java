package com.dabai.proxy.httpclient.huanong.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/13 19:04
 */
@Data
public class MemberInfoResp {

    private String phone;

    private String memberNo;

    @JsonProperty("IsAgent")
    private Integer isAgent;

    private String comCode;
}
