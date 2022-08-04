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
public class UserWalletInfoQueryDTO {

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
    private Integer originalIdentityTag;

    @ApiModelProperty(value = "推广费总收入")
    private BigDecimal commisionAmount;

    @ApiModelProperty(value = "充值总收入")
    private BigDecimal manualAmount;

    @ApiModelProperty(value = "可提现金额")
    private BigDecimal availableAmount;

    @ApiModelProperty(value = "累计提现金额")
    private BigDecimal cashedAmount;

}
