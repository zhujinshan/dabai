package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/21 22:10
 */
@Data
@ApiModel(value = "微信手机号信息")
public class WxPhoneInfoReq {

    @ApiModelProperty(value = "encryptedData", required = true)
    private String encryptedData;

    @ApiModelProperty(value = "iv", required = true)
    private String iv;

    @ApiModelProperty(value = "父id", required = false)
    private Long parentId;
}
