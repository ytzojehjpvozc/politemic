package com.xbh.politemic.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xbh.politemic.biz.notice.builder.NoticeBuilder;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.notice.srv.BaseNoticeSrv;
import com.xbh.politemic.biz.post.builder.CommentBuilder;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.srv.BaseCommentSrv;
import com.xbh.politemic.biz.queue.builder.QueueBuilder;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.common.constant.QueueConstant;
import com.xbh.politemic.common.enums.post.CommentStatusEnum;
import com.xbh.politemic.common.enums.queue.QueueMsgTypeEnum;
import com.xbh.politemic.common.util.SensitiveWordFilter;
import com.xbh.politemic.common.util.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @AuditCommonQueue: 审核评论 queue
 * @author: ZBoHang
 * @time: 2021/12/16 16:50
 */
@Component
public class AuditCommentQueue {

    private static final Logger log = LoggerFactory.getLogger(AuditCommentQueue.class);

    /**
     * 用户id的key
     */
    private final String USER_ID_KEY = "userId";

    /**
     * 评论id的key
     */
    private final String COMMENT_ID_KEY = "commentId";
    /**
     * 评论内容key
     */
    private final String COMMENT_CONTENT_KEY = "content";

    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private RabbitTemplate commentRabbitTemplate;
    @Autowired
    private BaseCommentSrv baseCommentSrv;
    @Autowired
    private BaseNoticeSrv baseNoticeSrv;

    /**
     * 发送审核消息
     * @author: ZBoHang
     * @time: 2021/10/15 16:15
     */
    public void send(String msgContent, String correlationData) {
        this.commentRabbitTemplate.convertAndSend(QueueConstant.AUDIT_COMMENT_EXCHANGE_NAME, "", msgContent, new CorrelationData(correlationData));
    }

    /**
     * 监听队列
     * @author: ZBoHang
     * @time: 2021/10/15 16:17
     */
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = QueueConstant.AUDIT_COMMENT_EXCHANGE_BIND_QUEUE_NAME)
    public void msgConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        JSONObject msg = JSONObject.parseObject(message);
        log.info("@@@@@收到mq的消息: " + msg.toJSONString());
        String msgId = msg.getString(QueueConstant.MSG_ID_COLUMN_NAME);
        String commentId = msg.getString(this.COMMENT_ID_KEY);
        String userId = msg.getString(this.USER_ID_KEY);
        String content = msg.getString(this.COMMENT_CONTENT_KEY);
        // 告知mq 数据已收到
        try {
            channel.basicAck(tag, Boolean.FALSE);
        } catch (IOException e) {
            log.error("@@@@@>>>>>>>>rabbit ack指令发送失败");
            try {
                // 出现异常直接弃用 由定时任务重新推入消息队列
                channel.basicNack(tag, Boolean.FALSE, Boolean.FALSE);
            } catch (IOException ex) {
                log.error("@@@@@>>>>>>>>rabbit nack指令发送失败");
            }
            return;
        }
        // 审核评论
        boolean flag = SensitiveWordFilter.isContainsSensitiveWord(content);
        // 初始化评论审核完成的通知
        Notice notice = NoticeBuilder.buildUnreadStatusNotice(userId);
        // 审核完成则去修改队列消息表中的消息状态 并 修改帖子的审核状态
        String NOTICE_CONTENT = "评论审核完成,审核结果: {}!";
        if (flag) {
            // 若含有敏感词汇
            notice.setContent(StrUtil.format(NOTICE_CONTENT, "未通过"));
            //  1-发表后待审核  2-正常  3-精华帖  4-管理删除、审核未通过的拉黑帖
            this.consumerOverModifyMsgStatus(msgId, commentId, CommentStatusEnum.SHIELD.getCode(), notice);
        } else {
            // 无敏感词汇
            notice.setContent(StrUtil.format(NOTICE_CONTENT, "通过"));
            //  1-发表后待审核  2-正常  3-精华帖  4-管理删除、审核未通过的拉黑帖   同类中未开启事务方法调用事务方法，事务不生效
            this.consumerOverModifyMsgStatus(msgId, commentId, CommentStatusEnum.NORMAL.getCode(), notice);
        }
    }

    /**
     * 消息消费完成修改消息状态 并添加用户通知
     * @author: ZBoHang
     * @time: 2021/10/15 16:21
     */
    void consumerOverModifyMsgStatus(String msgId, String commentId, String commentAuditStatus, Notice notice) {
        // 保存通知
        this.baseNoticeSrv.insertSelective(notice);
        // 构建一个带有审核状态的评论
        Comment comment = CommentBuilder.buildCommentWithAuditStatus(commentId, commentAuditStatus);
        // 修改评论的状态
        this.baseCommentSrv.updateByPrimaryKeySelective(comment);
        // 构建一个被消费状态的消息
        QueueMsg queueMsg = QueueBuilder.buildConsumedMsg(msgId);
        // 修改消息状态
        this.baseQueueSrv.updateByPrimaryKeySelective(queueMsg);
    }

    /**
     * 创建校验评论消息并发送至队列
     * @author: ZBoHang
     * @time: 2021/10/19 15:34
     */
    @Transactional(rollbackFor = Exception.class)
    public void createAuditCommentMsg(Comment comment) {
        Map<String, String> map = new HashMap<>(7);
        String msgId = StrKit.getUUID();

        map.put(QueueConstant.MSG_ID_COLUMN_NAME, msgId);
        map.put(this.COMMENT_ID_KEY, comment.getId());
        map.put(this.USER_ID_KEY, comment.getUserId());
        map.put(this.COMMENT_CONTENT_KEY, comment.getContent());
        String msgContent = JSONUtil.toJsonStr(map);
        // 初始化消息        --type->消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息 3-评论审核消息
        QueueMsg queueMsg = QueueBuilder.buildInitMsg(msgId, comment.getUserId(), msgContent, QueueMsgTypeEnum.MSG_AUDIT_COMMENT.getCode());
        // 持久化消息
        this.baseQueueSrv.insertSelective(queueMsg);
        // 发送至队列
        this.send(msgContent, msgId);
    }
}
