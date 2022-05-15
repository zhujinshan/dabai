package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:10
 */
@Data
@ApiModel(value = "保单查询")
public class PolicyInfoPageReq {

    @ApiModelProperty(value = "状态：0已退款 1已完成")
    private Integer status;

    @ApiModelProperty(value = "分页", required = true)
    private Paging paging;
}
