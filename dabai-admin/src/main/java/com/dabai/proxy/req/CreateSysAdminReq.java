package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/31 12:20
 */
@Data
@ApiModel(value = "新建系统管理员")
public class CreateSysAdminReq {

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "角色 1", required = true)
    private Integer role;

    private String organizationCode;

    private Boolean charge;

}
