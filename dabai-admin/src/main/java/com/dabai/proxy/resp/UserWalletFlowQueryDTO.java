package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 17:34
 */
@Data
public class UserWalletFlowQueryDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "推广人id")
    private Long parentId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "会员手机号")
    private String mobile;

    @ApiModelProperty(value = "会员编码")
    private String code;

    @ApiModelProperty(value = "所属机构编码")
    private String organizationCode;

    @ApiModelProperty(value = "邀请人编码")
    private String parentCode;

//    @ApiModelProperty(value = "邀请人姓名")
//    private String parentName;
//
//    @ApiModelProperty(value = "邀请人手机号")
//    private String parentMobile;

    @ApiModelProperty(value = "注册时间")
    private Date ctime;

    @ApiModelProperty(value = "是否转化代理人")
    private Boolean changeAgent;

    @ApiModelProperty(value = "原注册身份")
    private Integer registerIdentityTag;

    @ApiModelProperty(value = "钱包流水时间")
    private Date walletCtime;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "收支类型")
    private Integer flowType;

    @ApiModelProperty(value = "充值类型")
    private String manualChargeType;

    @ApiModelProperty(value = "充值人电话号")
    private String cname;

//    @ApiModelProperty(value = "财产险费用")
//    private BigDecimal propertyInsuranceFee;
//
//    @ApiModelProperty(value = "总费用")
//    private BigDecimal totalFee;
//
//    @ApiModelProperty(value = "健康险推广费用")
//    private BigDecimal healthInsurancePromotionFee = BigDecimal.ZERO;
//
//    @ApiModelProperty(value = "意外险推广费用")
//    private BigDecimal accidentInsurancePromotionFee = BigDecimal.ZERO;
//
//    @ApiModelProperty(value = "财产险推广费用")
//    private BigDecimal propertyInsurancePromotionFee = BigDecimal.ZERO;
//
//    @ApiModelProperty(value = "总推广费用")
//    private BigDecimal totalPromotionFee = BigDecimal.ZERO;

}
