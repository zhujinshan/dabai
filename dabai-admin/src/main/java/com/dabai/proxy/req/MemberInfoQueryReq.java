package com.dabai.proxy.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/7/30 15:38
 */
@Data
@ApiModel(value = "会员人员清单查询入参")
public class MemberInfoQueryReq {

    @ApiModelProperty(value = "所属机构")
    private String organizationCode;

    @ApiModelProperty(value = "会员姓名")
    private String name;

    @ApiModelProperty(value = "会员手机号")
    private String mobile;

    @ApiModelProperty(value = "会员编码")
    private String code;

    @ApiModelProperty(value = "会员状态：1 已实名、0 未实名、-1 全部")
    private Integer status;

    @ApiModelProperty(value = "邀请人会员编码")
    private String parentCode;

    @ApiModelProperty(value = "邀请人实名姓名")
    private String parentName;

    @ApiModelProperty(value = "邀请人手机号")
    private String parentMobile;

    @ApiModelProperty(value = "注册开始时间")
    private Date registerStartTime;

    @ApiModelProperty(value = "注册结束时间")
    private Date redisterEndtime;

    @ApiModelProperty(value = "会员身份证")
    private String idCard;

    @ApiModelProperty(value = "是否转化代理人 0:否 1：是")
    private Integer changeAgent;

    @ApiModelProperty(value = "转化代理人开始时间")
    private Date changeAgentStartTime;

    @ApiModelProperty(value = "转化代理人开始时间")
    private Date changeAgentEndtime;

    @ApiModelProperty(value = "原注册身份 0 会员 1代理人")
    private Integer originalIdentityTag;

    @ApiModelProperty(value = "实名认证开始时间")
    private Date realNameStartTime;

    @ApiModelProperty(value = "实名认证结束时间")
    private Date realNameEndTime;

    @ApiModelProperty(value = "分页信息")
    private Paging paging;
}
