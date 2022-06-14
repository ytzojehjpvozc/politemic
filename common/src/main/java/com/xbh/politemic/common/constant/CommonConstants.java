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
     * 咖啡因 分页帖子列表 刷新间隔 单位 s
     */
    public static final long CAFFEINE_CONFIG_REFRESH_SPACE_PAGE_GET_POSTS = 60 * 5;

    /**
     * 咖啡因 分页讨论帖 过期时间 单位 s
     */
    public static final long CAFFEINE_CONFIG_EXPIRE = 60 * 60;

    /**
     * coffeine中分页讨论帖的键 前缀
     */
    public static final String CAFFEINE_CONFIG_PAGE_GET_POSTS_PRE = "Caffeine_pageGetPosts_";

    /**
     * es 中帖子的索引名称
     */
    public static final String ES_POST_INDEX_NAME = "posts_index";

    /**
     * redis中幂等性 校验的key的前缀
     */
    public static final String REDIS_PRE_KEY_NAME_IDEMPOTENT = "Idempotent-";

}
