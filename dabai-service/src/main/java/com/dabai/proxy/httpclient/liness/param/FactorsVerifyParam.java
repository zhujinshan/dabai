package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 三要素校验入参
 *
 * {
 * 	merchant_id:”testMerchant001”,
 * partner_id:”jrmf”,
 * timestamp:”20180924235824387”,
 * sign_type:”RSA”,
 * sign:”iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==”,
 * certificate_type:”ID”,
 * certificate_no:”510108198908240613”,
 * name:”张三”,
 * bank_card_no:“4563510100873891034”
 * }
 */
@Data
public class FactorsVerifyParam extends LinessBaseParam{

    /**
     * 证件类型 当前固定上送ID
     */
    @JsonProperty("certificate_type")
    private String certificateType;

    /**
     * 证件号 身份证
     */
    @JsonProperty("certificate_no")
    private String certificateNo;

    /**
     * 持卡人姓名
     */
    private String name;

    /**
     * 卡号
     */
    @JsonProperty("bank_card_no")
    private String bankCardNo;
}
