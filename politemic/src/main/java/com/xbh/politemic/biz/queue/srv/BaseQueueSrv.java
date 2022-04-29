package com.xbh.politemic.biz.queue.srv;

import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.mapper.QueueMsgMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseQueueSrv: base 队列消息 srv
 * @author: ZBoHang
 * @time: 2021/12/9 16:15
 */
@Service
public class BaseQueueSrv extends CommonSrv<QueueMsg> {

    @Resource
    protected QueueMsgMapper queueMsgMapper;

}
