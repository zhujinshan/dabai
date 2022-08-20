package com.dabai.proxy.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_action_statistics")
public class UserActionStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_date")
    private Date actionDate;

    /**
     * 组织机构
     */
    private String organization;

    /**
     * 注册用户数
     */
    @Column(name = "t_register_amount")
    private Long tRegisterAmount;

    /**
     * t-1注册用户数
     */
    @Column(name = "t1_register_amount")
    private Long t1RegisterAmount;

    /**
     * 7日注册用户数
     */
    @Column(name = "t7_register_amount")
    private Long t7RegisterAmount;

    /**
     * 30日注册用户数
     */
    @Column(name = "t30_register_amount")
    private Long t30RegisterAmount;

    /**
     * 30日总注册用户数
     */
    @Column(name = "month_register_amount")
    private Long monthRegisterAmount;

    /**
     * 访问用户数
     */
    @Column(name = "t1_visit_amount")
    private Long t1VisitAmount;

    /**
     * 7日访问用户数
     */
    @Column(name = "t7_visit_amount")
    private Long t7VisitAmount;

    /**
     * 30日访问用户数
     */
    @Column(name = "t30_visit_amount")
    private Long t30VisitAmount;

    /**
     * dau
     */
    private Long dau;

    /**
     * mau
     */
    private Long mau;

    /**
     * 邀请用户
     */
    @Column(name = "invite_users")
    private String inviteUsers;

    /**
     * 邀请成功用户
     */
    @Column(name = "invite_success_users")
    private String inviteSuccessUsers;

    /**
     * 邀请成功下单用户
     */
    @Column(name = "invite_order_users")
    private String inviteOrderUsers;

    /**
     * 邀请代理用户
     */
    @Column(name = "invite_agent_users")
    private String inviteAgentUsers;

    /**
     * 被邀请用户
     */
    @Column(name = "invited_users")
    private String invitedUsers;

    /**
     * 被邀请下单用户
     */
    @Column(name = "invited_order_users")
    private String invitedOrderUsers;

    /**
     * 被邀请代理用户
     */
    @Column(name = "invited_agent_users")
    private String invitedAgentUsers;

    /**
     * 7日沉默用户
     */
    @Column(name = "t7_silent_users")
    private String t7SilentUsers;

    /**
     * 30日沉默用户
     */
    @Column(name = "t30_silent_users")
    private String t30SilentUsers;

    /**
     * 历史7天沉默用户
     */
    @Column(name = "old_t7_silent_users")
    private String oldT7SilentUsers;

    /**
     * 历史30天沉默用户
     */
    @Column(name = "ole_t30_silent_users")
    private String oleT30SilentUsers;

    private Date ctime;

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
     * @return action_date
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * @param actionDate
     */
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    /**
     * 获取组织机构
     *
     * @return organization - 组织机构
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * 设置组织机构
     *
     * @param organization 组织机构
     */
    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    /**
     * 获取注册用户数
     *
     * @return t_register_amount - 注册用户数
     */
    public Long gettRegisterAmount() {
        return tRegisterAmount;
    }

    /**
     * 设置注册用户数
     *
     * @param tRegisterAmount 注册用户数
     */
    public void settRegisterAmount(Long tRegisterAmount) {
        this.tRegisterAmount = tRegisterAmount;
    }

    /**
     * 获取t-1注册用户数
     *
     * @return t1_register_amount - t-1注册用户数
     */
    public Long getT1RegisterAmount() {
        return t1RegisterAmount;
    }

    /**
     * 设置t-1注册用户数
     *
     * @param t1RegisterAmount t-1注册用户数
     */
    public void setT1RegisterAmount(Long t1RegisterAmount) {
        this.t1RegisterAmount = t1RegisterAmount;
    }

    /**
     * 获取7日注册用户数
     *
     * @return t7_register_amount - 7日注册用户数
     */
    public Long getT7RegisterAmount() {
        return t7RegisterAmount;
    }

    /**
     * 设置7日注册用户数
     *
     * @param t7RegisterAmount 7日注册用户数
     */
    public void setT7RegisterAmount(Long t7RegisterAmount) {
        this.t7RegisterAmount = t7RegisterAmount;
    }

    /**
     * 获取30日注册用户数
     *
     * @return t30_register_amount - 30日注册用户数
     */
    public Long getT30RegisterAmount() {
        return t30RegisterAmount;
    }

    /**
     * 设置30日注册用户数
     *
     * @param t30RegisterAmount 30日注册用户数
     */
    public void setT30RegisterAmount(Long t30RegisterAmount) {
        this.t30RegisterAmount = t30RegisterAmount;
    }

    /**
     * 获取30日总注册用户数
     *
     * @return month_register_amount - 30日总注册用户数
     */
    public Long getMonthRegisterAmount() {
        return monthRegisterAmount;
    }

    /**
     * 设置30日总注册用户数
     *
     * @param monthRegisterAmount 30日总注册用户数
     */
    public void setMonthRegisterAmount(Long monthRegisterAmount) {
        this.monthRegisterAmount = monthRegisterAmount;
    }

    /**
     * 获取访问用户数
     *
     * @return t1_visit_amount - 访问用户数
     */
    public Long getT1VisitAmount() {
        return t1VisitAmount;
    }

    /**
     * 设置访问用户数
     *
     * @param t1VisitAmount 访问用户数
     */
    public void setT1VisitAmount(Long t1VisitAmount) {
        this.t1VisitAmount = t1VisitAmount;
    }

    /**
     * 获取7日访问用户数
     *
     * @return t7_visit_amount - 7日访问用户数
     */
    public Long getT7VisitAmount() {
        return t7VisitAmount;
    }

    /**
     * 设置7日访问用户数
     *
     * @param t7VisitAmount 7日访问用户数
     */
    public void setT7VisitAmount(Long t7VisitAmount) {
        this.t7VisitAmount = t7VisitAmount;
    }

    /**
     * 获取30日访问用户数
     *
     * @return t30_visit_amount - 30日访问用户数
     */
    public Long getT30VisitAmount() {
        return t30VisitAmount;
    }

    /**
     * 设置30日访问用户数
     *
     * @param t30VisitAmount 30日访问用户数
     */
    public void setT30VisitAmount(Long t30VisitAmount) {
        this.t30VisitAmount = t30VisitAmount;
    }

    /**
     * 获取dau
     *
     * @return dau - dau
     */
    public Long getDau() {
        return dau;
    }

    /**
     * 设置dau
     *
     * @param dau dau
     */
    public void setDau(Long dau) {
        this.dau = dau;
    }

    /**
     * 获取mau
     *
     * @return mau - mau
     */
    public Long getMau() {
        return mau;
    }

    /**
     * 设置mau
     *
     * @param mau mau
     */
    public void setMau(Long mau) {
        this.mau = mau;
    }

    /**
     * 获取邀请用户
     *
     * @return invite_users - 邀请用户
     */
    public String getInviteUsers() {
        return inviteUsers;
    }

    /**
     * 设置邀请用户
     *
     * @param inviteUsers 邀请用户
     */
    public void setInviteUsers(String inviteUsers) {
        this.inviteUsers = inviteUsers == null ? null : inviteUsers.trim();
    }

    /**
     * 获取邀请成功用户
     *
     * @return invite_success_users - 邀请成功用户
     */
    public String getInviteSuccessUsers() {
        return inviteSuccessUsers;
    }

    /**
     * 设置邀请成功用户
     *
     * @param inviteSuccessUsers 邀请成功用户
     */
    public void setInviteSuccessUsers(String inviteSuccessUsers) {
        this.inviteSuccessUsers = inviteSuccessUsers == null ? null : inviteSuccessUsers.trim();
    }

    /**
     * 获取邀请成功下单用户
     *
     * @return invite_order_users - 邀请成功下单用户
     */
    public String getInviteOrderUsers() {
        return inviteOrderUsers;
    }

    /**
     * 设置邀请成功下单用户
     *
     * @param inviteOrderUsers 邀请成功下单用户
     */
    public void setInviteOrderUsers(String inviteOrderUsers) {
        this.inviteOrderUsers = inviteOrderUsers == null ? null : inviteOrderUsers.trim();
    }

    /**
     * 获取邀请代理用户
     *
     * @return invite_agent_users - 邀请代理用户
     */
    public String getInviteAgentUsers() {
        return inviteAgentUsers;
    }

    /**
     * 设置邀请代理用户
     *
     * @param inviteAgentUsers 邀请代理用户
     */
    public void setInviteAgentUsers(String inviteAgentUsers) {
        this.inviteAgentUsers = inviteAgentUsers == null ? null : inviteAgentUsers.trim();
    }

    /**
     * 获取被邀请用户
     *
     * @return invited_users - 被邀请用户
     */
    public String getInvitedUsers() {
        return invitedUsers;
    }

    /**
     * 设置被邀请用户
     *
     * @param invitedUsers 被邀请用户
     */
    public void setInvitedUsers(String invitedUsers) {
        this.invitedUsers = invitedUsers == null ? null : invitedUsers.trim();
    }

    /**
     * 获取被邀请下单用户
     *
     * @return invited_order_users - 被邀请下单用户
     */
    public String getInvitedOrderUsers() {
        return invitedOrderUsers;
    }

    /**
     * 设置被邀请下单用户
     *
     * @param invitedOrderUsers 被邀请下单用户
     */
    public void setInvitedOrderUsers(String invitedOrderUsers) {
        this.invitedOrderUsers = invitedOrderUsers == null ? null : invitedOrderUsers.trim();
    }

    /**
     * 获取被邀请代理用户
     *
     * @return invited_agent_users - 被邀请代理用户
     */
    public String getInvitedAgentUsers() {
        return invitedAgentUsers;
    }

    /**
     * 设置被邀请代理用户
     *
     * @param invitedAgentUsers 被邀请代理用户
     */
    public void setInvitedAgentUsers(String invitedAgentUsers) {
        this.invitedAgentUsers = invitedAgentUsers == null ? null : invitedAgentUsers.trim();
    }

    /**
     * 获取7日沉默用户
     *
     * @return t7_silent_users - 7日沉默用户
     */
    public String getT7SilentUsers() {
        return t7SilentUsers;
    }

    /**
     * 设置7日沉默用户
     *
     * @param t7SilentUsers 7日沉默用户
     */
    public void setT7SilentUsers(String t7SilentUsers) {
        this.t7SilentUsers = t7SilentUsers == null ? null : t7SilentUsers.trim();
    }

    /**
     * 获取30日沉默用户
     *
     * @return t30_silent_users - 30日沉默用户
     */
    public String getT30SilentUsers() {
        return t30SilentUsers;
    }

    /**
     * 设置30日沉默用户
     *
     * @param t30SilentUsers 30日沉默用户
     */
    public void setT30SilentUsers(String t30SilentUsers) {
        this.t30SilentUsers = t30SilentUsers == null ? null : t30SilentUsers.trim();
    }

    /**
     * 获取历史7天沉默用户
     *
     * @return old_t7_silent_users - 历史7天沉默用户
     */
    public String getOldT7SilentUsers() {
        return oldT7SilentUsers;
    }

    /**
     * 设置历史7天沉默用户
     *
     * @param oldT7SilentUsers 历史7天沉默用户
     */
    public void setOldT7SilentUsers(String oldT7SilentUsers) {
        this.oldT7SilentUsers = oldT7SilentUsers == null ? null : oldT7SilentUsers.trim();
    }

    /**
     * 获取历史30天沉默用户
     *
     * @return ole_t30_silent_users - 历史30天沉默用户
     */
    public String getOleT30SilentUsers() {
        return oleT30SilentUsers;
    }

    /**
     * 设置历史30天沉默用户
     *
     * @param oleT30SilentUsers 历史30天沉默用户
     */
    public void setOleT30SilentUsers(String oleT30SilentUsers) {
        this.oleT30SilentUsers = oleT30SilentUsers == null ? null : oleT30SilentUsers.trim();
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
}