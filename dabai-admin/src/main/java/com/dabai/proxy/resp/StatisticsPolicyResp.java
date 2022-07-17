package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 10:43
 */
@Data
@ApiModel(value = "业绩统计")
public class StatisticsPolicyResp {

    @ApiModelProperty(value = "累计业绩会员数量")
    private Long totalMemberAmount;

    @ApiModelProperty(value = "昨日业绩会员数量")
    private Long yesterdayMemberAmount;

    @ApiModelProperty(value = "近30日业绩会员数量")
    private Long monthMemberAmount;

    @ApiModelProperty(value = "累计业绩金额")
    private BigDecimal totalPolicyAmount;

    @ApiModelProperty(value = "昨日业绩金额")
    private BigDecimal yesterdayPolicyAmount;

    @ApiModelProperty(value = "近30日业绩金额")
    private BigDecimal monthPolicyAmount;
}
