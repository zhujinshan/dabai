package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhangwenzhe03
 * @Date: 2022/6/14 3:43 下午
 */
@Data
@ApiModel(value = "HBX会员身份变更")
public class UserPlateformReq {

    @ApiModelProperty(value = "hbx会员编码", required = true)
    private String code;

    @ApiModelProperty(value = "代理人身份标签", required = true)
    private String identityTag;

    @ApiModelProperty(value = "会员归属机构", required = true)
    private String organizationCode;
}
