package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/21 22:10
 */
@Data
@ApiModel(value = "微信用户信息")
public class WxUserInfoReq {

    @ApiModelProperty(value = "signature", required = true)
    private String signature;

    @ApiModelProperty(value = "rawData", required = true)
    private String rawData;

    @ApiModelProperty(value = "encryptedData", required = true)
    private String encryptedData;

    @ApiModelProperty(value = "iv", required = true)
    private String iv;
}
