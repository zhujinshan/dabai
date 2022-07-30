package com.dabai.proxy.resp;

import com.dabai.proxy.req.ChargeExcelModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 14:51
 */
@Data
@ApiModel(value = "批量充值结果")
public class BatchChargeResp {

    @ApiModelProperty(value = "充值信息")
    private List<ChargeExcelModel> chargeList;
}
