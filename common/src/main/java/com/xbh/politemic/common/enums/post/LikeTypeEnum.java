package com.xbh.politemic.common.enums.post;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @LikeTypeEnum: 点赞类型枚举
 * @author: ZBoHang
 * @time: 2022/1/6 9:47
 */
@Getter
public enum LikeTypeEnum {


    POST("1", "点赞帖子"),
    Comment("2", "点赞评论");

    private String code;
    private String define;

    private LikeTypeEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 通过str 获取点赞类型  默认-点赞帖子
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2022/1/6 9:50
     */
    public static String getTypeByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (LikeTypeEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }
        // 默认点赞帖子
        return POST.getCode();
    }
}
