package com.dabai.proxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SucNotifyParam {

    /**
     * request_no : M201811082322120002
     * deal_no : IT201811082322130423
     * timestamp : 20181108232230387
     * sign_type : RSA
     * sign : iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==
     * deal_status : 00
     * deal_status_msg : 发放成功
     * account_date : 20181108
     */
    @JsonProperty("request_no")
    private String requestNo;

    @JsonProperty("deal_no")
    private String dealNo;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("sign_type")
    private String signType;

    private String sign;

    private String fee;

    @JsonProperty("deal_status")
    private String dealStatus;

    @JsonProperty("deal_status_msg")
    private String dealStatusMsg;

    @JsonProperty("account_date")
    private String accountDate;

    @JsonProperty("fail_detail_msg")
    private String failDetailMsg;

}
