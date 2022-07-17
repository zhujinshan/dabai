package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 10:43
 */
@Data
@ApiModel(value = "会员转化代理人统计")
public class StatisticsAgentChangeResp {

    @ApiModelProperty(value = "累计会员数量")
    private Long totalMemberAmount;

    @ApiModelProperty(value = "昨日会员数量")
    private Long yesterdayMemberAmount;

    @ApiModelProperty(value = "近30日会员数量")
    private Long monthMemberAmount;

}
