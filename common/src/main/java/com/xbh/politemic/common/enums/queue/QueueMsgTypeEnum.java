package com.xbh.politemic.common.enums.queue;

import lombok.Getter;

@Getter
public enum QueueMsgTypeEnum {

    MSG_SEND_EMAIL("0", "邮件发送消息"),
    MSG_TAKE_TAIL("1", "获取用户评论尾巴消息"),
    MSG_AUDIT_POST("2", "帖子审核消息"),
    MSG_AUDIT_COMMENT("3", "评论审核消息");

    private final String code;
    private final String define;

    private QueueMsgTypeEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }
}
