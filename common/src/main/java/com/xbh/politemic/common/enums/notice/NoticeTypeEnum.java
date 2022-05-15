package com.xbh.politemic.common.enums.notice;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @NoticeTypeEnum: 通知/私信 类型 枚举
 * @author: ZBoHang
 * @time: 2021/12/14 13:52
 */
@Getter
public enum NoticeTypeEnum {

    NOTICE("notice", "通知"),
    LETTER("letter", "私信");

    private String code;
    private String define;

    private NoticeTypeEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取通知类型 默认通知类型
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/20 16:28
     */
    public static String getTyptByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (NoticeTypeEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }

        return NOTICE.getCode();
    }

}
