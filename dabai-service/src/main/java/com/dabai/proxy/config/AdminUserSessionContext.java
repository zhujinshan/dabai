package com.dabai.proxy.config;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/9 21:26
 */
public class AdminUserSessionContext {

    public static final ThreadLocal<AdminUserSessionInfo> USER_HOLDER = new ThreadLocal<>();


    public static void setAdminUserSessionInfo(AdminUserSessionInfo user) {
        USER_HOLDER.set(user);
    }

    public static AdminUserSessionInfo getAdminUserSessionInfo() {
        return USER_HOLDER.get();
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}
