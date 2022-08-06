package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:38
 */
@Data
@ApiModel(value = "会员收支清单查询入参")
public class MemberWalletFlowQueryReq {

    @ApiModelProperty(value = "所属机构")
    private String organizationCode;

    @ApiModelProperty(value = "会员姓名")
    private String name;

    @ApiModelProperty(value = "会员手机号")
    private String mobile;

    @ApiModelProperty(value = "会员编码")
    private String code;

    @ApiModelProperty(value = "邀请人会员编码")
    private String parentCode;

    @ApiModelProperty(value = "流水时间范围-开始时间")
    private Date walletStartTime;

    @ApiModelProperty(value = "流水时间范围-结束时间")
    private Date walletEndTime;

    @ApiModelProperty(value = "是否转化代理人 0:否 1：是")
    private Integer changeAgent;

    @ApiModelProperty(value = "原注册身份 0 会员 1代理人")
    private Integer originalIdentityTag;

    @ApiModelProperty(value = "收支类型：1：收入 2：提现 3: 退款 4：人工充值")
    private Integer flowType;

    @ApiModelProperty(value = "充值类型：A 为展业费，B 为运营激励费，C 为基本法费")
    private String manualChargeType;

    @ApiModelProperty(value = "费用min")
    private BigDecimal minAmount;

    @ApiModelProperty(value = "费用max")
    private BigDecimal maxAmount;

    @ApiModelProperty(value = "分页信息")
    private Paging paging;
}
