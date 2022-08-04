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
@ApiModel(value = "会员收支查询结果")
public class MemberWalletFlowQueryResp {

    @ApiModelProperty(value = "总量")
    private Long total;

    @ApiModelProperty(value = "会员收支列表")
    private List<UserWalletFlowQueryDTO> userWalletList;
}
