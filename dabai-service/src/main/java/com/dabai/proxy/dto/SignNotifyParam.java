package com.dabai.proxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignNotifyParam {
    @JsonProperty("serial_no")
    private String serialNo;

    @JsonProperty("deal_no")
    private String dealNo;
    /**
     * S 代表成功
     */
    @JsonProperty("ret_code")
    private String retCode;

    @JsonProperty("ret_msg")
    private String retMsg;

    private String timestamp;

    @JsonProperty("sign_type")
    private String signType;

    private String sign;

}
