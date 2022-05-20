package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:10
 */
@Data
@ApiModel(value = "提现记录查询")
public class CashInfoPageReq {

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "分页", required = true)
    private Paging paging;
}
