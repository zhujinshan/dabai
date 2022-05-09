package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryTransferParam extends LinessBaseParam{

    /**
     * 商户下发请求单号
     * 两者至少输入其一；均输入则以deal_no进行查询
     */
    @JsonProperty("request_no")
    private String requestNo;

    /**
     * 融赋通处理单
     */
    @JsonProperty("deal_no")
    private String dealNo;

}
