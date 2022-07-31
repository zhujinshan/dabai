package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 18:10
 */
@Data
@ApiModel(value = "会员清单查询结果")
public class MemberInfoQueryResp {

    @ApiModelProperty(value = "总量")
    private Long total;

    @ApiModelProperty(value = "会员列表")
    private List<UserInfoQueryDTO> userList;
}