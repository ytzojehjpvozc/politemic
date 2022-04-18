package com.xbh.politemic.common.util;

import org.springframework.core.NamedThreadLocal;

/**
 * @ThreadLocalUtils: 本地线程工具类
 * @author: ZBoHang
 * @time: 2021/10/13 9:47
 */
public class ThreadLocalUtils {

    /**
     * 用户id
     */
    private static ThreadLocal<String> userId = new NamedThreadLocal<>("本地用户id线程");

    private ThreadLocalUtils() {

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
     * 清理本地线程副本中的用户id
     * @author: ZBoHang
     * @time: 2021/10/13 14:52
     */
    public static void remove() {
        userId.remove();
    }
}
