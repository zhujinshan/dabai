package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/14 12:12
 */
@Data
@ApiModel(value = "userInfo", description = "会员信息")
public class UserInfoResp {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "unionId")
    private String unionId;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "银行卡")
    private String bankCard;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "推广人")
    private Long parentUserId;

    @ApiModelProperty(value = "是否有效")
    private Integer valid;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "创建时间")
    private Date utime;

    @ApiModelProperty(value = "三方会员编号")
    private String memberNo;

    @ApiModelProperty(value = "华保星身份标签：1：会员 2：代理人")
    private Integer identityTag;

    @ApiModelProperty(value = "可用金额")
    private BigDecimal availableAmount;

    @ApiModelProperty(value = "已提现金额")
    private BigDecimal cashedAmount;

    @ApiModelProperty(value = "累计收入")
    private BigDecimal totalAmount;
}
