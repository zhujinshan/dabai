package com.dabai.proxy.httpclient.liness.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ret_data:{
 * 	deal_no:”IT201811082322130423”,
 * 	amount:” 5900.50”,
 * 	deal_status:”A0”,
 * 	deal_status_msg:”已受理”
 * }
 */
@Data
public class TransferToBankCardResult {
    /**
     * 融赋通平台处理单号
     */
    @JsonProperty("deal_no")
    private String dealNo;

    private String amount;

    /**
     * 下发状态码
     * A0	已受理
     * A1	受理失败
     * B0	下发打款中
     * C1	下发打款成功
     * C2	下发打款失败
     * C3	下发打款成功后被冲正
     */
    @JsonProperty("deal_status")
    private String dealStatus;


    @JsonProperty("deal_status_msg")
    private String dealStatusMsg;

}
