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
@ApiModel(value = "会员业绩清单查询入参")
public class MemberPerformQueryReq {

    @ApiModelProperty(value = "所属机构")
    private String organizationCode;

    @ApiModelProperty(value = "会员姓名")
    private String name;

    @ApiModelProperty(value = "会员手机号")
    private String mobile;

    @ApiModelProperty(value = "会员编码")
    private String code;

    @ApiModelProperty(value = "邀请人会员编码")
    private String parentCode;

    @ApiModelProperty(value = "时间范围-开始时间")
    private Date startTime;

    @ApiModelProperty(value = "时间范围-结束时间")
    private Date endtime;

    @ApiModelProperty(value = "是否转化代理人 0:否 1：是")
    private Integer changeAgent;

    @ApiModelProperty(value = "原注册身份 0 会员 1代理人")
    private Integer originalIdentityTag;

    @ApiModelProperty(value = "分页信息")
    private Paging paging;
}
