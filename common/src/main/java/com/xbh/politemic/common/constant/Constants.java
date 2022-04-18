package com.xbh.politemic.common.constant;

/**
 * 定义相关常量
 * @Author: zhengbohang
 * @Date: 2021/10/3 10:45
 */
public class Constants {

    /**
     * 请求头中的令牌名
     */
    public static final String TOKEN = "token";
    /**
     * 模块名称
     */
    public static final String USER_MODEL_NAME = "普通用户模块";

    public static final String ADMIN_MODEL_NAME = "管理人员模块";
    /**
     * 状态 Y
     */
    public static final String STATUS_Y = "Y";
    /**
     * 状态 N
     */
    public static final String STATUS_N = "N";
    /**
     * 空的字符串
     */
    public static final String SPACE_STRING  = " ";

    /**
     * 随机盐值的获取设置
     */
    public static final int VALIDATE_CODE_START_INDEX = 0;

    public static final int VALIDATE_CODE_END_INDEX = 7;
    /**
     * 储存在redis中用户token的前缀
     */
    public static final String USER_TOKEN_PRE = "User_Token_";

    /**
     * 默认注册状态
     */
    public static final String DEFAULT_REGISTER_STATUS = "0";
    /**
     * 默认注册权限等级
     */
    public static final String DEFAULT_REGISTER_AUTH_LEVEL = "2";
    /**
     * token的有效时间(ps:3天) 单位: ms
     */
    public static final long TOKEN_TIME_OUT = 1000 * 60 * 60 * 24 * 3;
    /**
     * 队列消息表中消息id的字段
     */
    public static final String MSG_ID_COLUMN_NAME = "id";
    /**
     * 队列消息表中消息类型的字段
     */
    public static final String MSG_TYPE_COLUMN_NAME = "type";
    /**
     * 队列消息表中消息类型 0-邮件消息
     */
    public static final String MSG_TYPE_EMAIL = "0";
    /**
     * 队列消息表中消息类型 1-获取用户评论尾巴消息
     */
    public static final String MSG_TYPE_TAIL = "1";
    /**
     * 队列消息表中消息类型 2-帖子审核消息
     */
    public static final String MSG_TYPE_AUDIT = "2";

    /**
     * 消息初始状态
     */
    public static final String MSG_INITIAL_STATUS = "0";
    /**
     * 消息进入队列状态
     */
    public static final String MSG_ENTER_QUEUE_STATUS = "1";
    /**
     * 消息被消费状态
     */
    public static final String MSG_CONSUMED_STATUS = "2";
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
     * 新建帖子初始状态 1-发表后待审核
     */
    public static final String CREATE_POST_INIT_STATUS = "1";
    /**
     * 帖子通过审核默认状态 2-正常
     */
    public static final String POST_AUDIT_PASS_STATUS = "2";
    /**
     * 帖子未通过审核状态 4-异常
     */
    public static final String POST_AUDIT_NONE_PASS_STATUS = "4";
    /**
     * 通知状态 0-未读 1-已读 2-删除
     */
    public static final String NOTICE_NONE_READ_STATUS = "0";
    /**
     * 通知状态 0-未读 1-已读 2-删除
     */
    public static final String NOTICE_HAVE_READ_STATUS = "1";
    /**
     * 通知状态 0-未读 1-已读 2-删除
     */
    public static final String NOTICE_INVALID_STATUS = "2";

}
