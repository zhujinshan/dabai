package com.dabai.proxy.config;

/**
 * @author: jinshan.zhu
 * @date: 2022/5/9 21:26
 */
public class UserSessionContext {

    public static final ThreadLocal<UserSessionInfo> USER_HOLDER = new ThreadLocal<>();


    public static void setSessionInfo(UserSessionInfo user) {
        USER_HOLDER.set(user);
    }

    public static UserSessionInfo getSessionInfo() {
        return USER_HOLDER.get();
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}
