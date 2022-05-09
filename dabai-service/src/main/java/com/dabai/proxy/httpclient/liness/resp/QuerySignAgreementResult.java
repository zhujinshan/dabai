package com.dabai.proxy.httpclient.liness.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ret_data:{
 * 		sign_status:”Y”,
 * 		contract_count:”2”,
 * sign_count:”2”
 * }
 */
@Data
public class QuerySignAgreementResult {
    /**
     * 签约状态
     * N-未完成所有必要签约
     * Y-已完成所有必要签约
     */
    @JsonProperty("sign_status")
    private String signStatus;

    /**
     * 协议数
     * 下发公司必须签约协议数
     */
    @JsonProperty("contract_count")
    private String contractCount;

    /**
     * 协议签约数
     * 用户已完成必须签约协议数
     */
    @JsonProperty("sign_count")
    private String signCount;
}
