package com.xbh.politemic.biz.queue.dto;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.common.constant.Constants;

import java.sql.Timestamp;

/**
 * @QueueDTO: 队列模型数据传输转换
 * @author: ZBoHang
 * @time: 2021/12/10 15:54
 */
public class QueueDTO {

    /**
     * 构建一个入队状态消息
     * @param msgId 消息id
     * @return: com.xbh.politemic.biz.queue.domain.QueueMsg
     * @author: ZBoHang
     * @time: 2021/12/10 15:57
     */
    public static QueueMsg buildOnQueueMsg(String msgId) {

        QueueMsg queueMsg = null;

        if (StrUtil.isNotBlank(msgId)) {
            // 消息id
            queueMsg = new QueueMsg().setId(msgId)
                    // 生产时间
                    .setProductTime(new Timestamp(System.currentTimeMillis()))
                    // 回执数据
                    .setMsgCorrelationData(msgId)
                    // 0-消息初始状态  1-消息进入队列状态  2-消息被消费状态
                    .setStatus(Constants.STATUS_STR_ONE);
        }
        return queueMsg;
    }

    /**
     * 构建一个被消费状态消息
     * @param msgId 消息id
     * @return: com.xbh.politemic.biz.queue.domain.QueueMsg
     * @author: ZBoHang
     * @time: 2021/12/10 15:57
     */
    public static QueueMsg buildConsumedMsg(String msgId) {

        QueueMsg queueMsg = null;

        if (StrUtil.isNotBlank(msgId)) {
            // 消息id
            queueMsg = new QueueMsg().setId(msgId)
                    // 0-消息初始状态  1-消息进入队列状态  2-消息被消费状态
                    .setStatus(Constants.STATUS_STR_TWO)
                    // 消费时间
                    .setConsumTime(new Timestamp(System.currentTimeMillis()));
        }
        return queueMsg;
    }

    /**
     * 构建一个不同类型的初始状态的消息
     * @param msgId 消息id
     * @param userId 关联用户id
     * @param msgContent 消息内容
     * @param type 消息类型
     * @return: com.xbh.politemic.biz.queue.domain.QueueMsg
     * @author: ZBoHang
     * @time: 2021/12/10 16:13
     */
    public static QueueMsg buildInitMsg(String msgId, String userId, String msgContent, String type) {

        QueueMsg queueMsg = null;

        if (StrUtil.isNotBlank(msgId)) {
            // 消息id
            queueMsg = new QueueMsg().setId(msgId)
                    // 关联用户id
                    .setUserId(userId)
                    .setMsgContent(msgContent)
                    // 0-消息初始状态  1-消息进入队列状态  2-消息被消费状态
                    .setStatus(Constants.STATUS_STR_ZERO)
                    // 队列消息表中消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息
                    .setType(type);
        }
        return queueMsg;
    }


}
