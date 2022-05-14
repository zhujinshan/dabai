package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 17:32
 */
@Data
@ApiModel(value = "用户签约")
public class UserCashSubmitReq {

    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    @ApiModelProperty(value = "身份证", required = true)
    private String idCard;

    @ApiModelProperty(value = "银行卡", required = true)
    private String bankCard;

    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty(value = "开户行", required = true)
    private String bankName;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "提现金额", required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
