package com.xbh.politemic.common.enums.post;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 评论类型枚举
 * @author: ZBoHang
 * @time: 2021/12/16 15:53
 */
@Getter
public enum CommentTypeEnum {

    TARGET_POSTS("0", "对帖子的评论"),
    TARGET_COMMENT("1", "对评论的评论");

    private final String code;
    private final String define;

    private CommentTypeEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取评论类型
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/16 15:58
     */
    public static String getTypeByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (CommentTypeEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }
        // 默认类型为 针对帖子的评论
        return TARGET_POSTS.getCode();
    }

}
