package com.dabai.proxy.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "policy_info")
public class PolicyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员平台id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 状态：（0已退款1已完成2已失效）
     */
    @Column(name = "policy_status")
    private Integer policyStatus;

    /**
     * 提成
     */
    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;

    /**
     * 保单号
     */
    @Column(name = "policy_no")
    private String policyNo;

    /**
     * 产品编码
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 投保人姓名
     */
    @Column(name = "insure_name")
    private String insureName;

    /**
     * 被保人姓名
     */
    @Column(name = "assured_name")
    private String assuredName;

    /**
     * 保障期间（年）
     */
    @Column(name = "ensure_time")
    private Integer ensureTime;

    /**
     * 保费（元）
     */
    private BigDecimal premium;

    /**
     * 投保起期 yyyy-MM-dd
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 投保止期 yyyy-MM-dd
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * 电子保单链接
     */
    @Column(name = "ele_policy_addr")
    private String elePolicyAddr;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
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
     * 获取会员平台id
     *
     * @return user_id - 会员平台id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置会员平台id
     *
     * @param userId 会员平台id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取状态：（0已退款1已完成2已失效）
     *
     * @return policy_status - 状态：（0已退款1已完成2已失效）
     */
    public Integer getPolicyStatus() {
        return policyStatus;
    }

    /**
     * 设置状态：（0已退款1已完成2已失效）
     *
     * @param policyStatus 状态：（0已退款1已完成2已失效）
     */
    public void setPolicyStatus(Integer policyStatus) {
        this.policyStatus = policyStatus;
    }

    /**
     * 获取提成
     *
     * @return commission_amount - 提成
     */
    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * 设置提成
     *
     * @param commissionAmount 提成
     */
    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    /**
     * 获取保单号
     *
     * @return policy_no - 保单号
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * 设置保单号
     *
     * @param policyNo 保单号
     */
    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    /**
     * 获取产品编码
     *
     * @return product_code - 产品编码
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 设置产品编码
     *
     * @param productCode 产品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    /**
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * 获取投保人姓名
     *
     * @return insure_name - 投保人姓名
     */
    public String getInsureName() {
        return insureName;
    }

    /**
     * 设置投保人姓名
     *
     * @param insureName 投保人姓名
     */
    public void setInsureName(String insureName) {
        this.insureName = insureName == null ? null : insureName.trim();
    }

    /**
     * 获取被保人姓名
     *
     * @return assured_name - 被保人姓名
     */
    public String getAssuredName() {
        return assuredName;
    }

    /**
     * 设置被保人姓名
     *
     * @param assuredName 被保人姓名
     */
    public void setAssuredName(String assuredName) {
        this.assuredName = assuredName == null ? null : assuredName.trim();
    }

    /**
     * 获取保障期间（年）
     *
     * @return ensure_time - 保障期间（年）
     */
    public Integer getEnsureTime() {
        return ensureTime;
    }

    /**
     * 设置保障期间（年）
     *
     * @param ensureTime 保障期间（年）
     */
    public void setEnsureTime(Integer ensureTime) {
        this.ensureTime = ensureTime;
    }

    /**
     * 获取保费（元）
     *
     * @return premium - 保费（元）
     */
    public BigDecimal getPremium() {
        return premium;
    }

    /**
     * 设置保费（元）
     *
     * @param premium 保费（元）
     */
    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    /**
     * 获取投保起期 yyyy-MM-dd
     *
     * @return start_date - 投保起期 yyyy-MM-dd
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 设置投保起期 yyyy-MM-dd
     *
     * @param startDate 投保起期 yyyy-MM-dd
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    /**
     * 获取投保止期 yyyy-MM-dd
     *
     * @return end_date - 投保止期 yyyy-MM-dd
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置投保止期 yyyy-MM-dd
     *
     * @param endDate 投保止期 yyyy-MM-dd
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    /**
     * 获取电子保单链接
     *
     * @return ele_policy_addr - 电子保单链接
     */
    public String getElePolicyAddr() {
        return elePolicyAddr;
    }

    /**
     * 设置电子保单链接
     *
     * @param elePolicyAddr 电子保单链接
     */
    public void setElePolicyAddr(String elePolicyAddr) {
        this.elePolicyAddr = elePolicyAddr == null ? null : elePolicyAddr.trim();
    }

    /**
     * 获取创建时间
     *
     * @return ctime - 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置创建时间
     *
     * @param ctime 创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取更新时间
     *
     * @return utime - 更新时间
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * 设置更新时间
     *
     * @param utime 更新时间
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }
}