package com.xbh.politemic.common.enums.queue;

import lombok.Getter;

/**
 * @QueueMsgStatusEnum: 队列消息状态枚举
 * @author: ZBoHang
 * @time: 2021/12/13 13:22
 */
@Getter
public enum QueueMsgStatusEnum {

    MSG_INIT("0", "消息初始状态"),
    MSG_JOIN_QUEUE("1", "消息进入队列状态"),
    MSG_CONSUMED("2", "消息被消费状态");

    private final String code;
    private final String define;

    private QueueMsgStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }
}
