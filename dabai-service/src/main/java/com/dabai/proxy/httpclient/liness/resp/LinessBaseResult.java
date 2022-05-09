package com.dabai.proxy.httpclient.liness.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LinessBaseResult<T> {

    /**
     * ret_code : 0000
     * ret_msg : 受理成功
     * timestamp : 20180924235824387
     * sign_type : RSA
     * sign : iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==
     * ret_data : {}
     */
    /**
     * 0000为指令接收受理成功。
     */
    @JsonProperty("ret_code")
    private String retCode;

    /**
     * 返回码相关信息
     */
    @JsonProperty("ret_msg")
    private String retMsg;

    private String timestamp;

    @JsonProperty("sign_type")
    private String signType;


    private String sign;

    /**
     * 返回结果json
     */
    @JsonProperty("ret_data")
    private T retData;

}
