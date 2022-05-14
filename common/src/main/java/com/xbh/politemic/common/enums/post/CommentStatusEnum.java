package com.xbh.politemic.common.enums.post;

import lombok.Getter;

/**
 * @CommentStatusEnum: 评论状态枚举
 * @author: ZBoHang
 * @time: 2021/12/16 16:03
 */
@Getter
public enum CommentStatusEnum {

    PENDING_REVIEW("1", "待审核"),
    NORMAL("2", "正常"),
    SHIELD("3", "已删除");

    private String code;
    private String define;

    private CommentStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }
}
