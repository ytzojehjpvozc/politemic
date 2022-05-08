package com.xbh.politemic.common.util;

import org.springframework.core.NamedThreadLocal;

/**
 * @ThreadLocalUtils: 本地线程工具类
 * @author: ZBoHang
 * @time: 2021/10/13 9:47
 */
public class ThreadLocalUtil {

    /**
     * 用户id
     */
    private static final ThreadLocal<String> userId = new NamedThreadLocal<>("本地用户id线程");

    /**
     * 用户令牌
     */
    private static final ThreadLocal<String> token = new NamedThreadLocal<>("本地用户令牌线程");

    private ThreadLocalUtil() {

    }

    /**
     * 获取本地线程副本中的用户id
     * @author: ZBoHang
     * @time: 2021/10/13 14:52
     */
    public static String getUserId() {
        return userId.get();
    }

    /**
     * 设置用户id至本地线程副本中
     * @author: ZBoHang
     * @time: 2021/10/13 14:52
     */
    public static void setUserId(String uId) {
        userId.set(uId);
    }

    /**
     * 获取本地线程副本中的用户令牌信息
     */
    public static String getToken() {
        return token.get();
    }

    /**
     * 设置本地线程副本中的用户令牌信息
     */
    public static void setToken(String toke) {
        token.set(toke);
    }

    /**
     * 清理本地线程副本中的用户id、用户令牌
     * @author: ZBoHang
     * @time: 2021/10/13 14:52
     */
    public static void removeAll() {
        userId.remove();
        token.remove();
    }
}
