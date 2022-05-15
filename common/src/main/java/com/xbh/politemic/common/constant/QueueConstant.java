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
     * 帖子审核、评论审核 交换机名
     */
    public static final String AUDIT_POST_EXCHANGE_NAME = "AuditPost";

    /**
     * 帖子审核、评论审核 交换机绑定的队列名
     */
    public static final String AUDIT_POST_EXCHANGE_BIND_QUEUE_NAME = "AuditPostQueue";

    /**
     * 帖子审核、评论审核 交换机名
     */
    public static final String AUDIT_COMMENT_EXCHANGE_NAME = "AuditComment";

    /**
     * 帖子审核、评论审核 交换机绑定的队列名
     */
    public static final String AUDIT_COMMENT_EXCHANGE_BIND_QUEUE_NAME = "AuditCommentQueue";

    /**
     * 队列消息表中消息id的字段
     */
    public static final String MSG_ID_COLUMN_NAME = "id";

    /**
     * 队列消息表中消息类型的字段
     */
    public static final String MSG_TYPE_COLUMN_NAME = "type";

    /**
     * 队列消息表中消息状态的字段
     */
    public static final String MSG_STATUS_COLUMN_NAME = "status";

    /**
     * 队列消息表中消息创建时间的字段
     */
    public static final String MSG_CREATE_TIME_COLUMN_NAME = "productTime";

    /**
     * 一天时间对应的毫秒数
     */
    public static final long ONE_DAY = 1000 * 60 * 60 * 24;
}
