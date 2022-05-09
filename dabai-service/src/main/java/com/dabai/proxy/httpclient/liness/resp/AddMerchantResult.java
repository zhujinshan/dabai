package com.dabai.proxy.httpclient.liness.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddMerchantResult {

    /**
     * 增加时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonProperty("add_time")
    private String addTime;

}
