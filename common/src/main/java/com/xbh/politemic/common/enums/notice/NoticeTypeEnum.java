package com.xbh.politemic.common.enums.notice;

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

}
