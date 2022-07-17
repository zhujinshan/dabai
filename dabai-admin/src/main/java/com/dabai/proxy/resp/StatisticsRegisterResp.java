package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/17 10:43
 */
@Data
@ApiModel(value = "注册统计")
public class StatisticsRegisterResp {

    private Integer totalAmount;

    private Integer yesterdayAmount;

    private Integer monthAmount;
}
