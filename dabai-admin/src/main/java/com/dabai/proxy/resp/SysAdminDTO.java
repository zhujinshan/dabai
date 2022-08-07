package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/31 13:09
 */
@Data
@ApiModel(value = "管理员信息")
public class SysAdminDTO {

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "角色 1超级管理员 2管理员 3普通用户")
    private Integer role;

    @ApiModelProperty(value = "所属组织编码")
    private String organizationCode;

    @ApiModelProperty(value = "是否可充值")
    private Boolean charge = false;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "拥有权限的模块")
    private List<Integer> modules;

}
