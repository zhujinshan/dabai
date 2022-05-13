package com.dabai.proxy.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_sign_info")
public class UserSignInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 签约主体：FC、ZX
     */
    @Column(name = "sign_source")
    private String signSource;

    /**
     * 下发公司：RFT XDB
     */
    @Column(name = "business_source")
    private String businessSource;

    /**
     * 签约状态 1 成功 2失败 3 签约中
     */
    @Column(name = "sign_status")
    private Byte signStatus;

    /**
     * 签约流水号
     */
    @Column(name = "sign_deal_no")
    private String signDealNo;

    /**
     * 城市地区
     */
    private String area;

    /**
     * 备注失败原因
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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取签约主体：FC、ZX
     *
     * @return sign_source - 签约主体：FC、ZX
     */
    public String getSignSource() {
        return signSource;
    }

    /**
     * 设置签约主体：FC、ZX
     *
     * @param signSource 签约主体：FC、ZX
     */
    public void setSignSource(String signSource) {
        this.signSource = signSource == null ? null : signSource.trim();
    }

    /**
     * 获取下发公司：RFT XDB
     *
     * @return business_source - 下发公司：RFT XDB
     */
    public String getBusinessSource() {
        return businessSource;
    }

    /**
     * 设置下发公司：RFT XDB
     *
     * @param businessSource 下发公司：RFT XDB
     */
    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource == null ? null : businessSource.trim();
    }

    /**
     * 获取签约状态 1 成功 2失败 3 签约中
     *
     * @return sign_status - 签约状态 1 成功 2失败 3 签约中
     */
    public Byte getSignStatus() {
        return signStatus;
    }

    /**
     * 设置签约状态 1 成功 2失败 3 签约中
     *
     * @param signStatus 签约状态 1 成功 2失败 3 签约中
     */
    public void setSignStatus(Byte signStatus) {
        this.signStatus = signStatus;
    }

    /**
     * 获取签约流水号
     *
     * @return sign_deal_no - 签约流水号
     */
    public String getSignDealNo() {
        return signDealNo;
    }

    /**
     * 设置签约流水号
     *
     * @param signDealNo 签约流水号
     */
    public void setSignDealNo(String signDealNo) {
        this.signDealNo = signDealNo == null ? null : signDealNo.trim();
    }

    /**
     * 获取城市地区
     *
     * @return area - 城市地区
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置城市地区
     *
     * @param area 城市地区
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 获取备注失败原因
     *
     * @return remark - 备注失败原因
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注失败原因
     *
     * @param remark 备注失败原因
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