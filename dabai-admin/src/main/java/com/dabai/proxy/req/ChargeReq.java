package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 17:45
 */
@Data
@ApiModel(value = "会员充值")
public class ChargeReq {

    @ApiModelProperty(value = "会员编码", required = true)
    private String code;

    @ApiModelProperty(value = "金额", required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "充值类型", required = true)
    private String chargeType;

    @ApiModelProperty(value = "会员id", required = false)
    private Long userId;
}
