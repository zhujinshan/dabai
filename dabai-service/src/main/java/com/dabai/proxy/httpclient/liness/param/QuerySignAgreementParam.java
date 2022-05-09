package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 签约状态查询入参
 * {
 * 	merchant_id:”testMerchant001”,
 * partner_id:”jrmf”,
 * timestamp:”20180924235824387”,
 * sign_type:”RSA”,
 * sign:”iewkmlm82lshdf9qohbk8aw2fnxy98w23rmsldfhihos2lmug24nfns8sasf3rsdfsffwe442sdgsddfoiu93rkls==”,
 * transfer_corp_id:”tongnutax”,
 * certificate_no:”510108198305040612”,
 * certificate_type:”ID”,
 * name:”张三”
 * }
 */
@Data
public class QuerySignAgreementParam extends LinessBaseParam{
    /**
     * 下发公司ID
     */
    @JsonProperty("transfer_corp_id")
    private String transferCorpId;
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
    @JsonProperty("transfer_corp_id")
    private String name;

}
