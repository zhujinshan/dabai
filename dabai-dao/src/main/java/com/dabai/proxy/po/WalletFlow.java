package com.dabai.proxy.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "wallet_flow")
public class WalletFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员平台id
     */
    @Column(name = "use_id")
    private Long useId;

    /**
     * 钱包id
     */
    @Column(name = "wallet_id")
    private Long walletId;

    /**
     * 金额（收入/提现/退款）
     */
    private BigDecimal amount;

    /**
     * 类型：1：收入 2：提现 3: 退款
     */
    @Column(name = "flow_type")
    private Integer flowType;

    /**
     * 保单编号
     */
    @Column(name = "policy_no")
    private String policyNo;

    /**
     * 提现请求单号
     */
    @Column(name = "cash_request_no")
    private String cashRequestNo;

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
     * 获取钱包id
     *
     * @return wallet_id - 钱包id
     */
    public Long getWalletId() {
        return walletId;
    }

    /**
     * 设置钱包id
     *
     * @param walletId 钱包id
     */
    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    /**
     * 获取金额（收入/提现/退款）
     *
     * @return amount - 金额（收入/提现/退款）
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额（收入/提现/退款）
     *
     * @param amount 金额（收入/提现/退款）
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取类型：1：收入 2：提现 3: 退款
     *
     * @return flow_type - 类型：1：收入 2：提现 3: 退款
     */
    public Integer getFlowType() {
        return flowType;
    }

    /**
     * 设置类型：1：收入 2：提现 3: 退款
     *
     * @param flowType 类型：1：收入 2：提现 3: 退款
     */
    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    /**
     * 获取保单编号
     *
     * @return policy_no - 保单编号
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * 设置保单编号
     *
     * @param policyNo 保单编号
     */
    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo == null ? null : policyNo.trim();
    }

    /**
     * 获取提现请求单号
     *
     * @return cash_request_no - 提现请求单号
     */
    public String getCashRequestNo() {
        return cashRequestNo;
    }

    /**
     * 设置提现请求单号
     *
     * @param cashRequestNo 提现请求单号
     */
    public void setCashRequestNo(String cashRequestNo) {
        this.cashRequestNo = cashRequestNo == null ? null : cashRequestNo.trim();
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