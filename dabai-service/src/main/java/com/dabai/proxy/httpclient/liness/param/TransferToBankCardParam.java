package com.dabai.proxy.httpclient.liness.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 银行卡下发
 */
@Data
public class TransferToBankCardParam extends LinessBaseParam{

    /**
     * transfer_corp_id : tongnutax
     * request_no : M201811082322120002
     * name : 张三
     * bank_card_no : 4563510100873891034
     * amount : 5900.50
     * remark : 销售推广提成
     */
    /**
     * 下发公司ID
     */
    @JsonProperty("transfer_corp_id")
    private String transferCorpId;
    /**
     * 商户下发请求单号
     */
    @JsonProperty("request_no")
    private String requestNo;
    /**
     * 持卡人姓名
     */
    private String name;
    /**
     * 银行卡号
     */
    @JsonProperty("bank_card_no")
    private String bankCardNo;

    /**
     * 打款金额
     */
    private String amount;
    /**
     * 证件号
     */
    @JsonProperty("certificate_no")
    private String certificateNo;

    /**
     * 回调地址
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    private String remark;

}
