package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:36
 */
@Data
@ApiModel(value = "用户提现信息")
public class UserCashSignInfoResp {

    @ApiModelProperty(value = "用户编码")
    private Long userId;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "银行卡")
    private String bankCard;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "可用金额")
    private BigDecimal availableAmount;

    @ApiModelProperty(value = "签约状态")
    private Integer signStatus;

}
