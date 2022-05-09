package com.dabai.proxy.httpclient.liness.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ret_data:{
 * request_no:”M201811082322120002”,
 * 			deal_no:”IT201811082322130423”,
 * 			deal_status:”C1”,
 * 			deal_status_msg:”下发打款成功”,
 * 			account_date:”20181108”,
 * 			fee:”5.25”
 *                }
 */
@Data
public class QueryTransferResult {

    /**
     * 商户下发请求单号
     */
    @JsonProperty("request_no")
    private String requestNo;

    /**
     * 融赋通处理单号
     */
    @JsonProperty("deal_no")
    private String dealNo;

    /**
     * 下发处理状态
     * A0	已受理
     * A1	受理失败
     * B0	下发打款中
     * C1	下发打款成功
     * C2	下发打款失败
     * C3	下发打款成功后被冲正
     */
    @JsonProperty("deal_status")
    private String dealStatus;

    /**
     * 下发处理状态信息
     */
    @JsonProperty("deal_status_msg")
    private String dealStatusMsg;

    /**
     * 账期日
     * 若下发处理状态为成功，则该笔下发的账期日回传
     */
    @JsonProperty("account_date")
    private String accountDate;

    /**
     * 该笔下发手续费
     * 若下发处理状态为成功，则该笔下发的手续费回传
     */
    private String fee;
}
