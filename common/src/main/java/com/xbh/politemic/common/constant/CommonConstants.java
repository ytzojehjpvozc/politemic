package com.xbh.politemic.common.constant;

/**
 * 定义相关常量
 * @Author: zhengbohang
 * @Date: 2021/10/3 10:45
 */
public class CommonConstants {

    /**
     * 模块名称 普通用户模块
     */
    public static final String USER_MODEL_NAME = "普通用户模块";

    /**
     * 模块名称 管理人员模块
     */
    public static final String ADMIN_MODEL_NAME = "管理人员模块";

    /**
     * 状态 Y
     */
    public static final String STATUS_Y = "Y";

    /**
     * 状态 N
     */
    public static final String STATUS_N = "N";

    /**
     * 异常报告
     */
    public static final String EXCEPTIONS_REPORT = "请求发生错误,异常编号: {} ,请联系管理员处理";

    /**
     * 日志埋点key
     */
    public static final String MDC_KEY = "USER_ID";

    /**
     * APPLICATION_JSON
     */
    public static final String APPLICATION_JSON_CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * CHARACTER_ENCODING
     */
    public static final String CHARACTER_ENCODING_CONTENT_TYPE = "UTF-8";

    /**
     * 咖啡因 初始容量
     */
    public static final int CAFFEINE_CONFIG_INIT_CAPACITY = 36;

    /**
     * 咖啡因 最大容量
     */
    public static final long CAFFEINE_CONFIG_MAX_SIZE = 180;

    /**
     * 咖啡因 刷新间隔 单位 s
     */
    public static final long CAFFEINE_CONFIG_REFRESH_SPACE = 60 * 10;

    /**
     * 咖啡因 过期时间 单位 s
     */
    public static final long CAFFEINE_CONFIG_EXPIRE = 60 * 60;

}
