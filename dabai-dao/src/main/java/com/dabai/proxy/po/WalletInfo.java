package com.dabai.proxy.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "wallet_info")
public class WalletInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员平台id
     */
    @Column(name = "use_id")
    private Long useId;

    /**
     * 可用金额
     */
    @Column(name = "available_amount")
    private BigDecimal availableAmount;

    /**
     * 已提现金额
     */
    @Column(name = "cashed_amount")
    private BigDecimal cashedAmount;

    /**
     * 累计收入
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 上一次提现时间
     */
    @Column(name = "last_cash_time")
    private Date lastCashTime;

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
     * 获取会员平台id
     *
     * @return use_id - 会员平台id
     */
    public Long getUseId() {
        return useId;
    }

    /**
     * 设置会员平台id
     *
     * @param useId 会员平台id
     */
    public void setUseId(Long useId) {
        this.useId = useId;
    }

    /**
     * 获取可用金额
     *
     * @return available_amount - 可用金额
     */
    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    /**
     * 设置可用金额
     *
     * @param availableAmount 可用金额
     */
    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    /**
     * 获取已提现金额
     *
     * @return cashed_amount - 已提现金额
     */
    public BigDecimal getCashedAmount() {
        return cashedAmount;
    }

    /**
     * 设置已提现金额
     *
     * @param cashedAmount 已提现金额
     */
    public void setCashedAmount(BigDecimal cashedAmount) {
        this.cashedAmount = cashedAmount;
    }

    /**
     * 获取累计收入
     *
     * @return total_amount - 累计收入
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置累计收入
     *
     * @param totalAmount 累计收入
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取上一次提现时间
     *
     * @return last_cash_time - 上一次提现时间
     */
    public Date getLastCashTime() {
        return lastCashTime;
    }

    /**
     * 设置上一次提现时间
     *
     * @param lastCashTime 上一次提现时间
     */
    public void setLastCashTime(Date lastCashTime) {
        this.lastCashTime = lastCashTime;
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