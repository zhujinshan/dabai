package com.dabai.proxy.httpclient.liness.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignAgreementResult {

    @JsonProperty("serial_no")
    private String serialNo;

    @JsonProperty("deal_no")
    private String dealNo;
}
