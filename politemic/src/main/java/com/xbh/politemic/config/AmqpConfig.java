package com.xbh.politemic.config;

import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * @AmqpConfig: 自定义amqp配置
 * @author: ZBoHang
 * @time: 2021/10/14 13:31
 */
@Configuration
public class AmqpConfig {

    private static final Logger log = LoggerFactory.getLogger(AmqpConfig.class);

    @Autowired
    private BaseQueueSrv baseQueueSrv;

    /**
     * email队列init
     */
    @Bean
    public Queue initEmailQueue() {
        return new Queue(Constants.EMAIL_EXCHANGE_BIND_QUEUE_NAME);
    }
    /**
     * tail交换机init
     */
    @Bean
    public FanoutExchange initEmailExchange() {
        return ExchangeBuilder.fanoutExchange(Constants.EMAIL_EXCHANGE_NAME).build();
    }
    /**
     * 队列与交换机的绑定
     */
    @Bean
    public Binding initEmailBind(@Qualifier("initEmailQueue") Queue queue, @Qualifier("initEmailExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    /**
     * tail队列init
     */
    @Bean
    public Queue initTailQueue() {
        return new Queue(Constants.TAIL_EXCHANGE_BIND_QUEUE_NAME);
    }
    /**
     * tail交换机init
     */
    @Bean
    public FanoutExchange initTailExchange() {
        return ExchangeBuilder.fanoutExchange(Constants.TAIL_EXCHANGE_NAME).build();
    }
    /**
     * 队列与交换机的绑定
     */
    @Bean
    public Binding initTailBind(@Qualifier("initTailQueue") Queue queue, @Qualifier("initTailExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    /**
     * 帖子审核队列init
     */
    @Bean
    public Queue initAuditPostQueue() {
        return new Queue(Constants.AUDIT_POST_EXCHANGE_BIND_QUEUE_NAME);
    }
    /**
     * 帖子审核交换机init
     */
    @Bean
    public FanoutExchange initAuditPostExchange() {
        return ExchangeBuilder.fanoutExchange(Constants.AUDIT_POST_EXCHANGE_NAME).build();
    }
    /**
     * 帖子审核交换机与队列的bind
     */
    @Bean
    public Binding initAuditPostBind(@Qualifier("initAuditPostQueue") Queue queue, @Qualifier("initAuditPostExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    /**
     * 定义一个callback
     * @author: ZBoHang
     * @time: 2021/10/15 14:58
     */
    @Bean
    RabbitTemplate.ConfirmCallback initConfirmCallback() {
        return (correlationData, ack, cause) -> {
            String msgId = Optional.ofNullable(correlationData.getId()).orElse("未找见回执数据");
            // mq未收到消息 消息发送失败
            if (!ack) {
                log.info("@@@@@消息回调失败");
                return;
            }
            log.info("@@@@@消息回调,消息发送成功");
            // 修改队列消息表中的消息状态
            this.baseQueueSrv.updateByPrimaryKeySelective(new QueueMsg()
                    .setId(msgId)
                    .setProductTime(new Timestamp(System.currentTimeMillis()))
                    .setMsgCorrelationData(msgId)
                    .setStatus(Constants.MSG_ENTER_QUEUE_STATUS));
        };
    }

    /**
     * 发送邮件
     * @author: ZBoHang
     * @time: 2021/10/14 13:47
     */
    @Bean
    public RabbitTemplate emailRabbittemplate(ConnectionFactory connectionFactory,
                                              @Qualifier("initConfirmCallback") RabbitTemplate.ConfirmCallback confirmCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(Constants.EMAIL_EXCHANGE_NAME);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        return rabbitTemplate;
    }

    /**
     * 获取尾巴
     * @author: ZBoHang
     * @time: 2021/10/14 13:47
     */
    @Bean
    public RabbitTemplate tailRabbitTemplate(ConnectionFactory connectionFactory,
                                             @Qualifier("initConfirmCallback") RabbitTemplate.ConfirmCallback confirmCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(Constants.TAIL_EXCHANGE_NAME);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        return rabbitTemplate;
    }
    /**
     * 审核帖子
     * @author: ZBoHang
     * @time: 2021/10/15 15:45
     */
    @Bean
    public RabbitTemplate auditRabbitTemplate(ConnectionFactory connectionFactory,
                                              @Qualifier("initConfirmCallback") RabbitTemplate.ConfirmCallback confirmCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(Constants.AUDIT_POST_EXCHANGE_NAME);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        return rabbitTemplate;
    }
}
