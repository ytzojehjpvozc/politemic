package com.xbh.politemic.bean;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.queue.mapper.QueueMsgMapper;
import com.xbh.politemic.biz.user.mapper.SysUserMapper;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
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
    @Resource
    private QueueMsgMapper queueMsgMapper;
    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 向队列推送消息
     * @param msgContent: 消息主体内容
     * @param correlationData: 消息回执时带有的数据
     * @author: zhengbohang
     * @date: 2021/10/5 15:22
     */
    public void send(String msgContent, String correlationData) {
        this.tailRabbitTemplate.convertAndSend(Constants.TAIL_EXCHANGE_NAME, "", msgContent, new CorrelationData(correlationData));
    }

    /**
     * 监听队列中的消息并处理
     * @author: zhengbohang
     * @date: 2021/10/5 17:48
     */
    @RabbitListener(queues = Constants.TAIL_EXCHANGE_BIND_QUEUE_NAME)
    public void msgConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        JSONObject msg = JSONObject.parseObject(message);
        log.info("@@@@@收到mq的消息: " + msg.toJSONString());
        String msgId = msg.getString(Constants.MSG_ID_COLUMN_NAME);
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
            // 修改队列消息表中的消息状态
            this.customOverModifyMsgStatus(msgId, userId, tailStr);
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
        this.sysUserMapper.updateByPrimaryKeySelective(new SysUser().setId(userId).setTail(tailStr));
        // 修改尾巴消息 中id为 ** 的消息
        this.queueMsgMapper.updateByPrimaryKeySelective(new QueueMsg()
                .setId(msgId)
                .setStatus(Constants.MSG_CONSUMED_STATUS)
                .setConsumTime(new Timestamp(System.currentTimeMillis())));
    }

    /**
     * 创建获取尾巴消息并发送至消息队列
     * @author: zhengbohang
     * @date: 2021/10/5 17:49
     */
    @Transactional(rollbackFor = Exception.class)
    public void createTakeTailMsg(String userId, String takeTailUrl) {
        // 将用户数据放入消息中 供后续处理
        Map<String, String> map = new HashMap<>(3);
        String msgId = StrKit.getUUID();

        map.put(Constants.MSG_ID_COLUMN_NAME, msgId);
        map.put(this.USER_ID_KEY, userId);
        map.put(this.TAKE_TAIL_URL_KEY, takeTailUrl);
        String content = JSONObject.toJSONString(map);
        // 初始化激活邮件保存进数据库
        QueueMsg queueMsg = new QueueMsg()
                            .setId(msgId)
                            .setUserId(userId)
                            .setMsgContent(content)
                            .setStatus(Constants.MSG_INITIAL_STATUS)
                            .setType(Constants.MSG_TYPE_TAIL);
        // 推送队列前持久化
        this.queueMsgMapper.insertSelective(queueMsg);
        // 推入队列
        this.send(content, msgId);
    }
}
