package com.xbh.politemic.common.enums.notice;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @NoticeStatusEnum: 通知状态枚举
 * @author: ZBoHang
 * @time: 2021/12/13 13:27
 */
@Getter
public enum NoticeStatusEnum {

    UNREAD_STATUS("0", "未读状态"),
    READ_STATUS("1", "已读状态"),
    DELETE_STATUS("2", "删除状态");

    private final String code;
    private final String define;

    private NoticeStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取状态 默认 0-未读
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/14 13:46
     */
    public static String getStatusByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (NoticeStatusEnum value : NoticeStatusEnum.values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }
        // 都不满足 则默认未读
        return UNREAD_STATUS.getCode();
    }
}
