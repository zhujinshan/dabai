package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: jinshan.zhu
 * @date: 2022/8/21 00:47
 */
@Data
public class OperationalDataResp {

    private Long id;

    @ApiModelProperty(value = "日期")
    private Date actionDate;

    /**
     * 组织机构
     */
    @ApiModelProperty(value = "组织机构")
    private String organization;

    /**
     * 注册用户数
     */
    @ApiModelProperty(value = "注册用户数")
    private Long tRegisterAmount;


    @ApiModelProperty(value = "次日留存用户数")
    private Long t1VisitAmount;

    @ApiModelProperty(value = "7日留存用户数")
    private Long t7VisitAmount;

    @ApiModelProperty(value = "30日留存用户数")
    private Long t30VisitAmount;

    @ApiModelProperty(value = "次日留存率")
    private BigDecimal t1VisitRate;

    @ApiModelProperty(value = "7日留存率")
    private BigDecimal t7VisitRate;

    @ApiModelProperty(value = "30日留存率")
    private BigDecimal t30VisitRate;

    /**
     * dau
     */
    @ApiModelProperty(value = "dau")
    private Long dau;

    /**
     * mau
     */
    @ApiModelProperty(value = "mau")
    private Long mau;

    /**
     * dau
     */
    @ApiModelProperty(value = "日使用率")
    private BigDecimal dauRate;

    /**
     * mau
     */
    @ApiModelProperty(value = "月使用率")
    private BigDecimal mauRate;

    /**
     * 邀请用户
     */
    @ApiModelProperty(value = "邀请用户")
    private List<Long> inviteUsers;

    /**
     * 邀请成功用户
     */
    @ApiModelProperty(value = "邀请成功用户")
    private List<Long> inviteSuccessUsers;

    /**
     * 邀请成功下单用户
     */
    @ApiModelProperty(value = "邀请成功下单用户")
    private List<Long> inviteOrderUsers;

    /**
     * 邀请代理用户
     */
    @ApiModelProperty(value = "邀请代理用户")
    private List<Long> inviteAgentUsers;

    /**
     * 被邀请用户
     */
    @ApiModelProperty(value = "被邀请用户")
    private List<Long> invitedUsers;

    /**
     * 被邀请下单用户
     */
    @ApiModelProperty(value = "被邀请下单用户")
    private List<Long> invitedOrderUsers;

    /**
     * 被邀请代理用户
     */
    @ApiModelProperty(value = "被邀请代理用户")
    private List<Long> invitedAgentUsers;

    private Date ctime;
}
