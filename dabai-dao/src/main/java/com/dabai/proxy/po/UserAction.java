package com.dabai.proxy.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_action")
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private Date ctime;

    /**
     *  1: 登录 2:邀请
     */
    private Integer action;

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
     * 获取 1: 登录 2:邀请
     *
     * @return action -  1: 登录 2:邀请
     */
    public Integer getAction() {
        return action;
    }

    /**
     * 设置 1: 登录 2:邀请
     *
     * @param action  1: 登录 2:邀请
     */
    public void setAction(Integer action) {
        this.action = action;
    }
}