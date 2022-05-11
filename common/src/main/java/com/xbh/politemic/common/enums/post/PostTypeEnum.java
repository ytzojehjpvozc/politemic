package com.xbh.politemic.common.enums.post;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public enum PostTypeEnum {

    ORDINARY("1", "普通帖子"),
    TOP("2", "置顶帖子");

    private final String code;
    private final String define;

    private PostTypeEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取帖子类型 默认普通帖子
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/15 11:52
     */
    public static String getTypeByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (PostTypeEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }
        return ORDINARY.getCode();
    }

}
