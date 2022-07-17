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
@ApiModel(value = "提现统计")
public class StatisticsCashResp {

    @ApiModelProperty(value = "累计提现会员数量")
    private Long totalMemberAmount;

    @ApiModelProperty(value = "昨日提现会员数量")
    private Long yesterdayMemberAmount;

    @ApiModelProperty(value = "近30日提现会员数量")
    private Long monthMemberAmount;

    @ApiModelProperty(value = "累计提现金额")
    private BigDecimal totalCashedAmount;

    @ApiModelProperty(value = "昨日提现金额")
    private BigDecimal yesterdayCashedAmount;

    @ApiModelProperty(value = "近30日提现金额")
    private BigDecimal monthCashedAmount;
}
