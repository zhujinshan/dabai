package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:23
 */
@Data
@ApiModel(value = "提现列表结果")
public class CashInfoPageResult {

    @ApiModelProperty(value = "总数")
    private Long total;

    @ApiModelProperty(value = "列表")
    private List<CashInfoResp> list;
}
