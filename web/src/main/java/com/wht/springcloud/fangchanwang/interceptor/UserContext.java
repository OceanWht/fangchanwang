package com.wht.springcloud.fangchanwang.interceptor;

import com.wht.springcloud.fangchanwang.model.UserModel;

/**
 * 获取到用户对象之后，将对象放入threadlocal中
 */
public class UserContext {
    private static final ThreadLocal<UserModel> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 放入threadlocal
     * @param user
     */
    public static void setUser(UserModel user) {
        THREAD_LOCAL.set(user);
    }

    /**
     * 移除
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 获取
     * @return
     */
    public static UserModel getUser() {
        return THREAD_LOCAL.get();
    }
}
