package com.xbh.politemic.common.constant;

/**
 * @QueueConstant: 队列常量
 * @author: ZBoHang
 * @time: 2021/12/10 13:59
 */
public class QueueConstant {

    /**
     * 注册邮件消息交换机名
     */
    public static final String EMAIL_EXCHANGE_NAME = "SendEmail";

    /**
     * 激活邮件消息交换机绑定的队列名
     */
    public static final String EMAIL_EXCHANGE_BIND_QUEUE_NAME = "SendEmailQueue";

    /**
     * 尾巴交换机名称
     */
    public static final String TAIL_EXCHANGE_NAME = "TakeTail";

    /**
     * 尾巴交换机绑定的队列名
     */
    public static final String TAIL_EXCHANGE_BIND_QUEUE_NAME = "TailQueue";

    /**
     * 帖子审核交换机名
     */
    public static final String AUDIT_POST_EXCHANGE_NAME = "AuditPost";

    /**
     * 帖子审核交换机绑定的队列名
     */
    public static final String AUDIT_POST_EXCHANGE_BIND_QUEUE_NAME = "AuditPostQueue";

    /**
     * 队列消息表中消息id的字段
     */
    public static final String MSG_ID_COLUMN_NAME = "id";

    /**
     * 队列消息表中消息类型的字段
     */
    public static final String MSG_TYPE_COLUMN_NAME = "type";
}
