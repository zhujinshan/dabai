package com.dabai.proxy.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_admin")
public class SysAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 账号状态：1-正常 2-禁用
     */
    private Integer status;

    /**
     * 角色：1-超级管理员 2-管理员 3-普通账号
     */
    private Integer role;

    /**
     * 最近登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 是否可充值：0 否 1是
     */
    @Column(name = "can_charge")
    private Integer canCharge;

    /**
     * 所属机构编码
     */
    @Column(name = "organization_code")
    private String organizationCode;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 更新人
     */
    @Column(name = "update_user_id")
    private Long updateUserId;

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
     * 获取账号状态：1-正常 2-禁用
     *
     * @return status - 账号状态：1-正常 2-禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置账号状态：1-正常 2-禁用
     *
     * @param status 账号状态：1-正常 2-禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取角色：1-超级管理员 2-管理员 3-普通账号
     *
     * @return role - 角色：1-超级管理员 2-管理员 3-普通账号
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置角色：1-超级管理员 2-管理员 3-普通账号
     *
     * @param role 角色：1-超级管理员 2-管理员 3-普通账号
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取最近登录时间
     *
     * @return last_login_time - 最近登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近登录时间
     *
     * @param lastLoginTime 最近登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取是否可充值：0 否 1是
     *
     * @return can_charge - 是否可充值：0 否 1是
     */
    public Integer getCanCharge() {
        return canCharge;
    }

    /**
     * 设置是否可充值：0 否 1是
     *
     * @param canCharge 是否可充值：0 否 1是
     */
    public void setCanCharge(Integer canCharge) {
        this.canCharge = canCharge;
    }

    /**
     * 获取所属机构编码
     *
     * @return organization_code - 所属机构编码
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * 设置所属机构编码
     *
     * @param organizationCode 所属机构编码
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
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

    /**
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取更新人
     *
     * @return update_user_id - 更新人
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人
     *
     * @param updateUserId 更新人
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}