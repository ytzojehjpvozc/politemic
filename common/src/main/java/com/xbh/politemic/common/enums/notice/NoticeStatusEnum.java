package com.xbh.politemic.common.enums.notice;

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
}
