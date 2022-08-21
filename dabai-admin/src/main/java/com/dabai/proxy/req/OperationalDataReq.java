package com.dabai.proxy.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/21 00:58
 */
@Data
public class OperationalDataReq {

    @ApiModelProperty(value = "所属机构")
    private List<String> organizationCode;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
