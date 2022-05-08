package com.xbh.politemic.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xbh.politemic.biz.queue.builder.QueueBuilder;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.srv.BaseUserSrv;
import com.xbh.politemic.common.constant.QueueConstant;
import com.xbh.politemic.common.enums.queue.QueueMsgTypeEnum;
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
 * @TakeTailQueue: 获取评论尾巴
 * @author: ZBoHang
 * @time: 2021/10/14 11:11
 */
@Slf4j
@Component
public class TakeTailQueue {

    /**
     * 用户key
     */
    private final String USER_ID_KEY = "userId";
    /**
     * URL的key
     */
    private final String TAKE_TAIL_URL_KEY = "url";

    @Autowired
    private RabbitTemplate tailRabbitTemplate;
    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private BaseUserSrv baseUserSrv;


    /**
     * 向队列推送消息
     * @param msgContent: 消息主体内容
     * @param correlationData: 消息回执时带有的数据
     * @author: zhengbohang
     * @date: 2021/10/5 15:22
     */
    public void send(String msgContent, String correlationData) {
        this.tailRabbitTemplate.convertAndSend(QueueConstant.TAIL_EXCHANGE_NAME, "", msgContent, new CorrelationData(correlationData));
    }

    /**
     * 监听队列中的消息并处理
     * @author: zhengbohang
     * @date: 2021/10/5 17:48
     */
    @RabbitListener(queues = QueueConstant.TAIL_EXCHANGE_BIND_QUEUE_NAME)
    public void msgConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        JSONObject msg = JSONObject.parseObject(message);
        log.info("@@@@@收到mq的消息: " + msg.toJSONString());
        String msgId = msg.getString(QueueConstant.MSG_ID_COLUMN_NAME);
        String userId = msg.getString(this.USER_ID_KEY);
        String url = msg.getString(this.TAKE_TAIL_URL_KEY);
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
        // 尾巴str
        String tailStr = "";
        try {
            // api获取尾巴
            tailStr = HttpUtil.get(url);
        } catch (Exception e) {
            log.error("@@@@@>>>>>>>>>>>获取尾巴的http请求异常,消息消费状态未修改");
            return;
        }
        if (StrUtil.isNotBlank(tailStr)) {
            // 获取古诗文正文
            String content = JSONUtil.parseObj(tailStr).getStr("content");
            // 修改队列消息表中的消息状态
            this.customOverModifyMsgStatus(msgId, userId, content);
        }
    }

    /**
     * 消息消费完成后修改消息表中的状态
     * @author: ZBoHang
     * @time: 2021/10/12 17:32
     */
    @Transactional(rollbackFor = Exception.class)
    void customOverModifyMsgStatus(String msgId, String userId, String tailStr) {
        // 修改对应用户的尾巴
        this.baseUserSrv.updateByPrimaryKeySelective(new SysUser().setId(userId).setTail(tailStr));
        // 构建一个被消费状态的消息
        QueueMsg queueMsg = QueueBuilder.buildConsumedMsg(msgId);
        // 修改尾巴消息 中id为 ** 的消息
        this.baseQueueSrv.updateByPrimaryKeySelective(queueMsg);
    }

    /**
     * 创建获取尾巴消息并发送至消息队列
     * @author: zhengbohang
     * @date: 2021/10/5 17:49
     */
    @Transactional(rollbackFor = Exception.class)
    public void createTakeTailMsg(String userId, String takeTailUrl) {
        // 将用户数据放入消息中 供后续处理
        Map<String, String> map = new HashMap<>(5);
        String msgId = StrKit.getUUID();

        map.put(QueueConstant.MSG_ID_COLUMN_NAME, msgId);
        map.put(this.USER_ID_KEY, userId);
        map.put(this.TAKE_TAIL_URL_KEY, takeTailUrl);
        String content = JSONObject.toJSONString(map);
        // 初始化激活邮件 --type->消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息
        QueueMsg queueMsg = QueueBuilder.buildInitMsg(msgId, userId, content, QueueMsgTypeEnum.MSG_TAKE_TAIL.getCode());
        // 推送队列前持久化 保存进数据库
        this.baseQueueSrv.insertSelective(queueMsg);
        // 推入队列
        this.send(content, msgId);
    }
}
