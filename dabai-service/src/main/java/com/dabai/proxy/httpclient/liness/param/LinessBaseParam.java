package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 第三方公共参数域
 */
@Data
public class LinessBaseParam {

    /**
     * merchant_id : testMerchant001
     * partner_id : jrmf
     * timestamp : 20180924235824387
     * sign_type : RSA
     * sign : iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==
     */
    /**
     * 商户ID	由融赋通分配
     */
    @JsonProperty("merchant_id")
    private String merchantId;
    /**
     * 代理渠道ID	由融赋通分配
     */
    @JsonProperty("partner_id")
    private String partnerId;
    /**
     *交易发生时间戳，格式为
     * yyyyMMddHHmmssSSS
     */
    @JsonProperty("timestamp")
    private String timestamp;
    /**
     * 签名类型 RSA 或 SHA256
     */
    @JsonProperty("sign_type")
    private String signType;
    /**
     * 生成签名的base64编码
     */
    @JsonProperty("sign")
    private String sign;
}
