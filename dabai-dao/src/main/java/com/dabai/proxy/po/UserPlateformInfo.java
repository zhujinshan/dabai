package com.dabai.proxy.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_plateform_info")
public class UserPlateformInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员平台id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 华保星会员编码
     */
    private String code;

    /**
     * 用户所属平台：华保星用户
     */
    private String plateform;

    /**
     * 华保星身份标签：1：会员 2：代理人
     */
    @Column(name = "identity_tag")
    private Byte identityTag;

    /**
     * 所属机构编码：10北京
     */
    @Column(name = "organization_code")
    private String organizationCode;

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
     * 获取华保星会员编码
     *
     * @return code - 华保星会员编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置华保星会员编码
     *
     * @param code 华保星会员编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取用户所属平台：华保星用户
     *
     * @return plateform - 用户所属平台：华保星用户
     */
    public String getPlateform() {
        return plateform;
    }

    /**
     * 设置用户所属平台：华保星用户
     *
     * @param plateform 用户所属平台：华保星用户
     */
    public void setPlateform(String plateform) {
        this.plateform = plateform == null ? null : plateform.trim();
    }

    /**
     * 获取华保星身份标签：1：会员 2：代理人
     *
     * @return identity_tag - 华保星身份标签：1：会员 2：代理人
     */
    public Byte getIdentityTag() {
        return identityTag;
    }

    /**
     * 设置华保星身份标签：1：会员 2：代理人
     *
     * @param identityTag 华保星身份标签：1：会员 2：代理人
     */
    public void setIdentityTag(Byte identityTag) {
        this.identityTag = identityTag;
    }

    /**
     * 获取所属机构编码：10北京
     *
     * @return organization_code - 所属机构编码：10北京
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * 设置所属机构编码：10北京
     *
     * @param organizationCode 所属机构编码：10北京
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
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