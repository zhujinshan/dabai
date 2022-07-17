package com.dabai.proxy.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_tag_change")
public class UserTagChange {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 原身份标签
     */
    @Column(name = "original_identity_tag")
    private Integer originalIdentityTag;

    /**
     * 当前身份标签
     */
    @Column(name = "current_identity_tag")
    private Integer currentIdentityTag;

    private Date ctime;

    private Date utime;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
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
     * 获取原身份标签
     *
     * @return original_identity_tag - 原身份标签
     */
    public Integer getOriginalIdentityTag() {
        return originalIdentityTag;
    }

    /**
     * 设置原身份标签
     *
     * @param originalIdentityTag 原身份标签
     */
    public void setOriginalIdentityTag(Integer originalIdentityTag) {
        this.originalIdentityTag = originalIdentityTag;
    }

    /**
     * 获取当前身份标签
     *
     * @return current_identity_tag - 当前身份标签
     */
    public Integer getCurrentIdentityTag() {
        return currentIdentityTag;
    }

    /**
     * 设置当前身份标签
     *
     * @param currentIdentityTag 当前身份标签
     */
    public void setCurrentIdentityTag(Integer currentIdentityTag) {
        this.currentIdentityTag = currentIdentityTag;
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