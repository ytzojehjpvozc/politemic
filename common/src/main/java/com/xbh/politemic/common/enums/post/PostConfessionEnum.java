package com.xbh.politemic.common.enums.post;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @PostConfessionEnum: 帖子公开性枚举
 * @author: ZBoHang
 * @time: 2021/12/15 9:32
 */
@Getter
public enum PostConfessionEnum {

    CONFESSED("1", "公开性的"),
    PRIVACY("2", "私密性的");

    private final String code;
    private final String define;

    private PostConfessionEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取帖子的公开性
     */
    public static String getConfessionByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (PostConfessionEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }
        // 默认公开性的
        return CONFESSED.getCode();
    }
}
