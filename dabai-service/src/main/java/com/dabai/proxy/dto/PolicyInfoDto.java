package com.dabai.proxy.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 01:18
 */
@Data
public class PolicyInfoDto {

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 投保人姓名
     */
    private String insureName;

    /**
     * 被保人姓名
     */
    private String assuredName;

    /**
     * 保单号
     */
    private String policyNo;

    /**
     * 保障期间（年）
     */
    private Integer ensureTime;

    /**
     * 保费（元）
     */
    private BigDecimal premium;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 订单状态 （0已退款1已完成2已失效)
     */
    private String status;

    /**
     * 电子保单链接
     */
    private String elePolicyAddr;

    /**
     * 投保起期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 投保止期 yyyy-MM-dd
     */
    private String endDate;

    /**
     * 投保单号
     */
    private String applyNo;

    /**
     * 投保人证件号
     */
    private String insureCertificateNo;
}
