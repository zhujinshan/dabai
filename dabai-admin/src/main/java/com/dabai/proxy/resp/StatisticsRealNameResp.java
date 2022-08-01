package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 10:43
 */
@Data
@ApiModel(value = "实名认证统计")
public class StatisticsRealNameResp {

    private Long totalAmount;

    private Long yesterdayAmount;

    private Long monthAmount;
}
