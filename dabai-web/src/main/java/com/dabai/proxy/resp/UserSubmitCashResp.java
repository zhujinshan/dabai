package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 16:36
 */
@Data
@ApiModel(value = "提交提现结果")
public class UserSubmitCashResp {

    @ApiModelProperty(value = "状态：1：提交成功 2：提交失败")
    private Integer status;

    @ApiModelProperty(value = "流水号")
    private String dealNo;

    @ApiModelProperty(value = "原因")
    private String dealMsg;
}
