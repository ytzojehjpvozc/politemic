package com.xbh.politemic.common.constant;

/**
 * @UserConstant: 用户常量
 * @author: ZBoHang
 * @time: 2021/12/10 10:40
 */
public class UserConstant {

    /**
     * 请求头中的令牌名
     */
    public static final String TOKEN = "token";

    /**
     * 用户默认头像前缀 感谢https://github.com/multiavatar/multiavatar-php的接口
     */
    public static final String HEADER_URL_KEY_IN_ENV = "header_url";

    /**
     * 用户默认评论尾巴 感谢https://github.com/xenv/gushici的接口
     */
    public static final String TAIL_URL_KEY_IN_ENV = "tail_url";

    /**
     * 数据库中的token的有效时间(ps:3天) 单位: ms
     */
    public static final long TOKEN_TIME_OUT_IN_DB = 1000 * 60 * 60 * 24 * 3;

    /**
     * Redis中的token的有效时间(ps:3天) 单位: s
     */
    public static final long TOKEN_TIME_OUT_IN_REDIS = 60 * 60 * 24;

    /**
     * 储存在redis中用户token的前缀
     */
    public static final String USER_TOKEN_PRE = "User_Token_";

    /**
     * 随机盐值的获取设置 起始下标
     */
    public static final int SALT_START_INDEX = 0;

    /**
     * 随机盐值的获取设置 结束下标
     */
    public static final int SALT_END_INDEX = 6;

    /**
     * 随机盐值的获取设置 起始下标
     */
    public static final int VALIDATE_CODE_START_INDEX = 0;

    /**
     * 随机盐值的获取设置 结束下标
     */
    public static final int VALIDATE_CODE_END_INDEX = 7;
}
