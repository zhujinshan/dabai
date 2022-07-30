package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 21:45
 */
@Data
@ApiModel(value = "保单明细")
public class MemberPolicyDTO {

    /**
     * 会员平台id
     */
    @ApiModelProperty(value = "会员平台id")
    private Long userId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /**
     * 状态：（0已退款1已完成2已失效）
     */
    @ApiModelProperty(value = "状态：（0已退款1已完成2已失效）")
    private Integer policyStatus;

    /**
     * 提成
     */
    @ApiModelProperty(value = "提成")
    private BigDecimal commissionAmount;

    /**
     * 保单号
     */
    @ApiModelProperty(value = "保单号")
    private String policyNo;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 投保人姓名
     */
    @ApiModelProperty(value = "投保人姓名")
    private String insureName;

    /**
     * 被保人姓名
     */
    @ApiModelProperty(value = "被保人姓名")
    private String assuredName;

    /**
     * 保障期间（年）
     */
    @ApiModelProperty(value = "保障期间（年）")
    private Integer ensureTime;

    /**
     * 保费（元）
     */
    @ApiModelProperty(value = "保费（元）")
    private BigDecimal premium;

    /**
     * 投保起期 yyyy-MM-dd
     */
    @ApiModelProperty(value = "投保起期")
    private String startDate;

    /**
     * 投保止期 yyyy-MM-dd
     */
    @ApiModelProperty(value = "投保止期")
    private String endDate;

    /**
     * 电子保单链接
     */
    @ApiModelProperty(value = "电子保单链接")
    private String elePolicyAddr;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date utime;
}
