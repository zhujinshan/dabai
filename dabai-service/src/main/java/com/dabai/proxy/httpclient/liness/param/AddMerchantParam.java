package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 增加商户下发收款者
 * {
 * 	merchant_id:”testMerchant001”,
 * partner_id:”jrmf”,
 * timestamp:”20180924235824387”,
 * sign_type:”RSA”,
 * sign:”iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==”,
 * certificate_type:”ID”,
 * certificate_no:”510108198908240613”,
 * name:”张三”,
 * mobile_no:“13426432455”
 * }
 */
@Data
public class AddMerchantParam extends LinessBaseParam{
    /**
     * 证件类型	当前固定上送ID
     */
    @JsonProperty("certificate_type")
    private String certificateType;
    /**
     * 证件号	当前固定上送身份证号
     */
    @JsonProperty("certificate_no")
    private String certificateNo;
    /**
     * 下发者姓名
     */
    private String name;
    /**
     * 手机号
     */
    @JsonProperty("mobile_no")
    private String mobileNo;

}
