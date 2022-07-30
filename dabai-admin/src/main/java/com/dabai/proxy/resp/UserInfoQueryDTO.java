package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 17:34
 */
@Data
public class UserInfoQueryDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "推广人id")
    private Long parentId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "银行卡")
    private String bankCard;

    @ApiModelProperty(value = "会员编码")
    private String code;

    @ApiModelProperty(value = "所属机构编码")
    private String organizationCode;


    @ApiModelProperty(value = "会员状态：1 已实名、0 未实名")
    private Integer status;

    @ApiModelProperty(value = "邀请人编码")
    private String parentCode;

    @ApiModelProperty(value = "邀请人姓名")
    private String parentName;

    @ApiModelProperty(value = "邀请人手机号")
    private String parentMobile;

    @ApiModelProperty(value = "注册时间")
    private Date ctime;

    @ApiModelProperty(value = "实名认证时间")
    private Date realNameTime;

    @ApiModelProperty(value = "是否转化代理人")
    private Boolean changeAgent;

    @ApiModelProperty(value = "转化代理人时间")
    private Date changeAgentTime;

    @ApiModelProperty(value = "原身份标签")
    private Integer registerIdentityTag;
}
