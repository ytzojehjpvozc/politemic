package com.xbh.politemic.biz.queue.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class QueueMsg implements Serializable {
    /**
     * 消息id
     */
    @Id
    private String id;

    /**
     * 消息对应的用户id
     */
    private String userId;

    /**
     * 消息类型
      0:注册邮件消息
      1:获取评论尾巴消息
      2-帖子审核消息
     */
    private String type;

    /**
     * 消息回执数据
     */
    private String msgCorrelationData;

    /**
     * 消息状态
     0: 准备消息发送至队列
     1: 队列收到消息,准备发给消费者
     2: 消费者收到消息,处理
     */
    private String status;

    /**
     * 消息生产时间
     */
    private Date productTime;

    /**
     * 消息消费时间
     */
    private Date consumTime;

    /**
     * 消息主题内容
     */
    private String msgContent;

    private static final long serialVersionUID = 1L;
}