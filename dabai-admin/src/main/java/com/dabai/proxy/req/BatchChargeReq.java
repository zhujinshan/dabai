package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/16 17:45
 */
@Data
@ApiModel(value = "批量会员充值")
public class BatchChargeReq {

    @ApiModelProperty(value = "文件")
    private String file;

    @ApiModelProperty(value = "充值信息")
    private List<ChargeExcelModel> chargeList;

}
