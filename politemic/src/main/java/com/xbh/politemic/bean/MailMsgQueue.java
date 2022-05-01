package com.xbh.politemic.bean;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.dto.QueueDTO;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.common.constant.QueueConstant;
import com.xbh.politemic.common.util.StrKit;
import lombok.extern.slf4j.Slf4j;
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
 * @Description: 邮件处理队列
 * @Author: zhengbohang
 * @Date: 2021/10/5 14:47
 */
@Slf4j
@Component
public class MailMsgQueue {

    /**
     * 注册邮件主题key
     */
    private final String REGISTER_EMAIL_SUBJECT_KEY = "subject";
    /**
     * 注册邮件接收者key
     */
    private final String REGISTER_EMAIL_RECEIVER_KEY = "toUser";
    /**
     * 注册邮件内容key
     */
    private final String REGISTER_EMAIL_CONTENT_KEY = "content";
    /**
     * 注册邮件主题
     */
    private final String REGISTER_EMAIL_SUBJECT = "PM官方";
    /**
     * 注册邮件内容
     */
    private final String REGISTER_EMAIL_CONTENT = "邮件内容 一串带有用户信息和验证码的html内容 链接前端激活页面 页面初始化时请求后端激活接口 完成激活";


    @Autowired
    private RabbitTemplate emailRabbittemplate;
    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private MailClient mailClient;

    /**
     * 向队列推送消息
     * @param msgContent: 消息主体内容
     * @param correlationData: 消息回执时带有的数据
     * @author: zhengbohang
     * @date: 2021/10/5 15:22
     */
    public void send(String msgContent, String correlationData) {
        this.emailRabbittemplate.convertAndSend(QueueConstant.EMAIL_EXCHANGE_NAME, "", msgContent, new CorrelationData(correlationData));
    }

    /**
     * 监听队列中的消息并处理
     * @author: zhengbohang
     * @date: 2021/10/5 17:48
     */
    @RabbitListener(queues = QueueConstant.EMAIL_EXCHANGE_BIND_QUEUE_NAME)
    public void msgConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        JSONObject msg = JSONObject.parseObject(message);
        log.info("@@@@@收到mq的消息: " + msg.toJSONString());
        String msgId = msg.getString(QueueConstant.MSG_ID_COLUMN_NAME);
        String toUser = msg.getString(this.REGISTER_EMAIL_RECEIVER_KEY);
        String subject = msg.getString(this.REGISTER_EMAIL_SUBJECT_KEY);
        String content = msg.getString(this.REGISTER_EMAIL_CONTENT_KEY);
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
        // 发送激活邮件
        try {
            this.mailClient.sendEmail(toUser, subject, content);
        } catch (Exception e) {
            log.error("@@@@@>>>>>>>>>>>邮件发送异常 消息消费状态未修改");
            // 失败则返回,不更新状态
            return;
        }
        // 构建一个被消费状态的消息
        QueueMsg queueMsg = QueueDTO.buildConsumedMsg(msgId);
        // 邮件发送完成若无异常则更新消息状态
        this.baseQueueSrv.updateByPrimaryKeySelective(queueMsg);
    }

    /**
     * 创建激活邮件消息并发送至消息队列
     * @author: zhengbohang
     * @date: 2021/10/5 17:49
     * @param userId
     * @param email
     */
    @Transactional(rollbackFor = Exception.class)
    public void createActivateEmailMsg(String userId, String email) {
        // 将用户数据放入消息中 供后续处理
        Map<String, String> map = new HashMap<>(6);

        String msgId = StrKit.getUUID();

        map.put(QueueConstant.MSG_ID_COLUMN_NAME, msgId);
        map.put(this.REGISTER_EMAIL_RECEIVER_KEY, email);
        map.put(this.REGISTER_EMAIL_SUBJECT_KEY, this.REGISTER_EMAIL_SUBJECT);
        map.put(this.REGISTER_EMAIL_CONTENT_KEY,  this.REGISTER_EMAIL_CONTENT);
        String content = JSONObject.toJSONString(map);
        // 初始化激活邮件消息 队列消息表中消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息
        QueueMsg queueMsg = QueueDTO.buildInitMsg(msgId, userId, content, Constants.STATUS_STR_ZERO);
        // 注册邮件推送队列前持久化 保存进数据库
        this.baseQueueSrv.insertSelective(queueMsg);
        // 注册邮件推入队列
        this.send(content, msgId);
    }
}
