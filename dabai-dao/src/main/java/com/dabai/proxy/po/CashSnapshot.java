package com.dabai.proxy.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cash_snapshot")
public class CashSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员平台userId
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 商户下发请求单号
     */
    @Column(name = "request_no")
    private String requestNo;

    /**
     * 处理单号
     */
    @Column(name = "deal_no")
    private String dealNo;

    /**
     * 用户名
     */
    private String name;

    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 银行卡
     */
    @Column(name = "bank_card")
    private String bankCard;

    /**
     * 签约主体：FC/ZX
     */
    @Column(name = "sign_source")
    private String signSource;

    /**
     * 下发公司：RFT/XDB
     */
    @Column(name = "business_source")
    private String businessSource;

    /**
     * 提现状态:1-成功、2-失败、3-提现中
     */
    private Integer status;

    /**
     * 提现金额
     */
    @Column(name = "cashed_amount")
    private Long cashedAmount;

    /**
     * 三方接口返回结果
     */
    @Column(name = "third_response")
    private String thirdResponse;

    /**
     * 备注，失败原因等
     */
    private String remark;

    private Date ctime;

    private Date utime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取会员平台userId
     *
     * @return user_id - 会员平台userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置会员平台userId
     *
     * @param userId 会员平台userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取商户下发请求单号
     *
     * @return request_no - 商户下发请求单号
     */
    public String getRequestNo() {
        return requestNo;
    }

    /**
     * 设置商户下发请求单号
     *
     * @param requestNo 商户下发请求单号
     */
    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo == null ? null : requestNo.trim();
    }

    /**
     * 获取处理单号
     *
     * @return deal_no - 处理单号
     */
    public String getDealNo() {
        return dealNo;
    }

    /**
     * 设置处理单号
     *
     * @param dealNo 处理单号
     */
    public void setDealNo(String dealNo) {
        this.dealNo = dealNo == null ? null : dealNo.trim();
    }

    /**
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取身份证
     *
     * @return id_card - 身份证
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证
     *
     * @param idCard 身份证
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取银行卡
     *
     * @return bank_card - 银行卡
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * 设置银行卡
     *
     * @param bankCard 银行卡
     */
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    /**
     * 获取签约主体：FC/ZX
     *
     * @return sign_source - 签约主体：FC/ZX
     */
    public String getSignSource() {
        return signSource;
    }

    /**
     * 设置签约主体：FC/ZX
     *
     * @param signSource 签约主体：FC/ZX
     */
    public void setSignSource(String signSource) {
        this.signSource = signSource == null ? null : signSource.trim();
    }

    /**
     * 获取下发公司：RFT/XDB
     *
     * @return business_source - 下发公司：RFT/XDB
     */
    public String getBusinessSource() {
        return businessSource;
    }

    /**
     * 设置下发公司：RFT/XDB
     *
     * @param businessSource 下发公司：RFT/XDB
     */
    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource == null ? null : businessSource.trim();
    }

    /**
     * 获取提现状态:1-成功、2-失败、3-提现中
     *
     * @return status - 提现状态:1-成功、2-失败、3-提现中
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置提现状态:1-成功、2-失败、3-提现中
     *
     * @param status 提现状态:1-成功、2-失败、3-提现中
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取提现金额
     *
     * @return cashed_amount - 提现金额
     */
    public Long getCashedAmount() {
        return cashedAmount;
    }

    /**
     * 设置提现金额
     *
     * @param cashedAmount 提现金额
     */
    public void setCashedAmount(Long cashedAmount) {
        this.cashedAmount = cashedAmount;
    }

    /**
     * 获取三方接口返回结果
     *
     * @return third_response - 三方接口返回结果
     */
    public String getThirdResponse() {
        return thirdResponse;
    }

    /**
     * 设置三方接口返回结果
     *
     * @param thirdResponse 三方接口返回结果
     */
    public void setThirdResponse(String thirdResponse) {
        this.thirdResponse = thirdResponse == null ? null : thirdResponse.trim();
    }

    /**
     * 获取备注，失败原因等
     *
     * @return remark - 备注，失败原因等
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注，失败原因等
     *
     * @param remark 备注，失败原因等
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * @return utime
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * @param utime
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }
}