package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/21 22:10
 */
@Data
@ApiModel(value = "登录信息")
public class SysAdminLoginReq {

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "验证码", required = true)
    private String code;

}
