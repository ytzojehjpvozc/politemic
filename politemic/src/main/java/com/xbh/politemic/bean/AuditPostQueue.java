package com.xbh.politemic.bean;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xbh.politemic.biz.notice.builder.NoticeBuilder;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.notice.srv.BaseNoticeSrv;
import com.xbh.politemic.biz.post.builder.PostBuilder;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.srv.BasePostSrv;
import com.xbh.politemic.biz.queue.builder.QueueBuilder;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.common.constant.QueueConstant;
import com.xbh.politemic.common.enums.post.PostStatusEnum;
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
 * @AuditPostQueue: 帖子审核队列
 * @author: ZBoHang
 * @time: 2021/10/15 15:32
 */
@Component
public class AuditPostQueue {

    private static final Logger log = LoggerFactory.getLogger(AuditPostQueue.class);
    /**
     * 用户id的key
     */
    private final String USER_ID_KEY = "userId";
    /**
     * 帖子id的key
     */
    private final String POST_ID_KEY = "postId";
    /**
     * 帖子主题key
     */
    private final String POST_TITLE_KEY = "title";
    /**
     * 帖子内容key
     */
    private final String POST_CONTENT_KEY = "content";

    private final String NOTICE_CONTENT = "帖子审核完成,审核结果: {}!";

    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private RabbitTemplate auditRabbitTemplate;
    @Autowired
    private BasePostSrv basePostSrv;
    @Autowired
    private BaseNoticeSrv baseNoticeSrv;

    /**
     * 发送审核消息
     * @author: ZBoHang
     * @time: 2021/10/15 16:15
     */
    public void send(String msgContent, String correlationData) {
        this.auditRabbitTemplate.convertAndSend(QueueConstant.AUDIT_POST_EXCHANGE_NAME, "", msgContent, new CorrelationData(correlationData));
    }

    /**
     * 监听队列
     * @author: ZBoHang
     * @time: 2021/10/15 16:17
     */
    @RabbitListener(queues = QueueConstant.AUDIT_POST_EXCHANGE_BIND_QUEUE_NAME)
    public void msgConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        JSONObject msg = JSONObject.parseObject(message);
        log.info("@@@@@收到mq的消息: " + msg.toJSONString());
        String msgId = msg.getString(QueueConstant.MSG_ID_COLUMN_NAME);
        String postId = msg.getString(this.POST_ID_KEY);
        String userId = msg.getString(this.USER_ID_KEY);
        String title = msg.getString(this.POST_TITLE_KEY);
        String content = msg.getString(this.POST_CONTENT_KEY);
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
        // 审核帖子的主题和内容
        boolean flag = SensitiveWordFilter.isContainsSensitiveWord(title + content);
        // 初始化帖子审核完成的通知
        Notice notice = NoticeBuilder.buildUnreadStatusNotice(userId);
        // 审核完成则去修改队列消息表中的消息状态 并 修改帖子的审核状态
        if (flag) {
            // 若含有敏感词汇
            notice.setContent(StrUtil.format(this.NOTICE_CONTENT, "未通过"));
            //  1-发表后待审核  2-正常  3-精华帖  4-管理删除、审核未通过的拉黑帖
            this.customOverModifyMsgStatus(msgId, postId, PostStatusEnum.SHIELD.getCode(), notice);
        } else {
            // 无敏感词汇
            notice.setContent(StrUtil.format(this.NOTICE_CONTENT, "通过"));
            //  1-发表后待审核  2-正常  3-精华帖  4-管理删除、审核未通过的拉黑帖
            this.customOverModifyMsgStatus(msgId, postId, PostStatusEnum.NORMAL.getCode(), notice);
        }
    }

    /**
     * 消息消费完成修改消息状态 并添加用户通知
     * @author: ZBoHang
     * @time: 2021/10/15 16:21
     */
    @Transactional(rollbackFor = Exception.class)
    void customOverModifyMsgStatus(String msgId, String postId, String postAuditStatus, Notice notice) {
        // 保存通知
        this.baseNoticeSrv.insertSelective(notice);
        // 构建一个带有审核状态的讨论帖
        DiscussPosts discussPosts = PostBuilder.buildPostWithAuditStatus(postId, postAuditStatus);
        // 修改帖子的状态
        this.basePostSrv.updateByPrimaryKeySelective(discussPosts);
        // 构建一个被消费状态的消息
        QueueMsg queueMsg = QueueBuilder.buildConsumedMsg(msgId);
        // 修改消息状态
        this.baseQueueSrv.updateByPrimaryKeySelective(queueMsg);
    }

    /**
     * 创建校验帖子消息并发送至队列
     * @author: ZBoHang
     * @time: 2021/10/19 15:34
     */
    @Transactional(rollbackFor = Exception.class)
    public void createAuditPostMsg(DiscussPosts discussPosts) {
        Map<String, String> map = new HashMap<>(8);
        String msgId = StrKit.getUUID();

        map.put(QueueConstant.MSG_ID_COLUMN_NAME, msgId);
        map.put(this.POST_ID_KEY, discussPosts.getId());
        map.put(this.USER_ID_KEY, discussPosts.getUserId());
        map.put(this.POST_TITLE_KEY, discussPosts.getTitle());
        map.put(this.POST_CONTENT_KEY, discussPosts.getContent());
        String msgContent = JSONObject.toJSONString(map);
        // 初始化消息        --type->消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息
        QueueMsg queueMsg = QueueBuilder.buildInitMsg(msgId, discussPosts.getUserId(), msgContent, QueueMsgTypeEnum.MSG_AUDIT_POST.getCode());
        // 持久化消息
        this.baseQueueSrv.insertSelective(queueMsg);
        // 发送至队列
        this.send(msgContent, msgId);
    }
}
