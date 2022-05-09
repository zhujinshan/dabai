package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 签约入参
 * {
 * name:”熊大”
 * certificate_image:”ffffffffff000ffffff”
 * certificate_image_ background:”ffffffffff000ffffff”
 * merchant_id:”testMerchant001”,
 * partner_id:”jrmf”,
 * certificate_no:”542426198903087389”
 * certificate_type:”1”
 * mobile_no:”13261626001”
 * sign_agreement_type:”ALL”
 * sign:”iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==”,
 * notify_url:”http://www.jrmf360.com/”
 * serial_no:”A00001”
 * }
 */
@Data
public class SignAgreementParam extends LinessBaseParam{

    /**
     * 持卡人姓名
     */
    private String name;

    /**
     * 下发公司ID
     */
    @JsonProperty("transfer_corp_id")
    private String transferCorpId;

    /**
     * 证件号
     */
    @JsonProperty("certificate_no")
    private String certificateNo;

    /**
     * 证件类型
     * 1 身份证  2 港澳台通行证 3 护照  4 军官证
     */
    @JsonProperty("certificate_type")
    private String certificateType;

    /**
     * 手机号
     */
    @JsonProperty("mobile_no")
    private String mobileNo;

    /**
     * 签约选择
     * 传ALL为全签所有下发公司，非ALL则单签transfer_corp_id上送的下发公司
     */
    @JsonProperty("sign_agreement_type")
    private String signAgreementType;

    /**
     * 回调地址
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * 流水号
     */
    @JsonProperty("serial_no")
    private String serialNo;
}
