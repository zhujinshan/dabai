package com.dabai.proxy.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/15 14:24
 */
@Data
@ApiModel(value = "提现详情")
public class CashInfoResp {

    private Long id;

    /**
     * 会员平台userId
     */
    @ApiModelProperty(value = "会员平台id")
    private Long userId;

    /**
     * 商户下发请求单号
     */
    @ApiModelProperty(value = "商户下发请求单号")
    private String requestNo;

    /**
     * 处理单号
     */
    @ApiModelProperty(value = "处理单号")
    private String dealNo;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String name;

    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证")
    private String idCard;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 银行卡
     */
    @ApiModelProperty(value = "银行卡")
    private String bankCard;

    /**
     * 签约主体：FC/ZX
     */
    @ApiModelProperty(value = "签约主体：FC/ZX")
    private String signSource;

    /**
     * 下发公司：RFT/XDB
     */
    @ApiModelProperty(value = "下发公司：RFT/XDB")
    private String businessSource;

    /**
     * 提现状态:1-成功、2-失败、3-提现中
     */
    @ApiModelProperty(value = "提现状态:1-成功、2-失败、3-提现中")
    private Integer status;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private BigDecimal cashedAmount;

    /**
     * 备注，失败原因等
     */
    @ApiModelProperty(value = "备注，失败原因等")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date utime;

    /**
     * 三方接口返回结果
     */
    @ApiModelProperty(value = "三方接口返回结果")
    private String thirdResponse;
}
