package com.xbh.politemic.common.constant;

/**
 * @PostConstants: 帖子 常量
 * @author: ZBoHang
 * @time: 2022/1/6 10:27
 */
public class PostConstants {

    /**
     * 帖子点赞前缀 （针对于 哪个帖子）
     */
    public static final String REDIS_POST_LIKE_KEY_PRE = "POST-LIKE:";

    /**
     * 帖子点赞用户前缀 (针对于 哪个用户)
     */
    public static final String REDIS_POST_LIKE_USER_KEY_PRE = "POST_LIKE_USER:";
}
