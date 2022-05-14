package com.xbh.politemic.task;

import com.xbh.politemic.bean.AuditPostQueue;
import com.xbh.politemic.bean.MailMsgQueue;
import com.xbh.politemic.bean.TakeTailQueue;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.user.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @AsyncTask: 异步任务
 * @author: ZBoHang
 * @time: 2021/10/9 15:45
 */
@Component
public class AsyncTask {

    @Autowired
    private MailMsgQueue mailMsgQueue;
    @Autowired
    private TakeTailQueue takeTailQueue;
    @Autowired
    private AuditPostQueue auditPostQueue;

    /**
     * 新建激活邮件消息并发送
     * @author: ZBoHang
     * @time: 2021/10/14 10:55
     */
    @Async("CustomAsyncThreadPoolExecutor")
    public void createActivateEmailMsg (SysUser sysUser) {
        this.mailMsgQueue.createActivateEmailMsg(sysUser.getId(), sysUser.getEmail());
    }

    /**
     * 循环发送多个队列消息
     * @author: ZBoHang
     * @time: 2021/10/14 10:56
     */
    @Async("CustomAsyncThreadPoolExecutor")
    public void createActivateEmailMsgs (List<QueueMsg> list) {
        list.forEach(queueMsg -> this.mailMsgQueue.send(queueMsg.getMsgContent(), queueMsg.getMsgCorrelationData()));
    }

    /**
     * 异步调用api获取尾巴
     * @author: ZBoHang
     * @time: 2021/10/14 17:52
     */
    @Async("CustomAsyncThreadPoolExecutor")
    public void getTail(String userId, String tailUrl) {
        this.takeTailQueue.createTakeTailMsg(userId, tailUrl);
    }

    /**
     * 异步调用api获取尾巴,循环发送多个
     * @author: ZBoHang
     * @time: 2021/10/14 17:52
     */
    @Async("CustomAsyncThreadPoolExecutor")
    public void getTails(List<QueueMsg> list) {
        list.forEach(queueMsg -> this.takeTailQueue.send(queueMsg.getMsgContent(), queueMsg.getMsgCorrelationData()));
    }

    /**
     * 帖子异步审核
     * @author: ZBoHang
     * @time: 2021/10/15 15:20
     */
    @Async("CustomAsyncThreadPoolExecutor")
    public void auditPost(DiscussPosts discussPosts) {
        this.auditPostQueue.createAuditPostMsg(discussPosts);
    }

    /**
     * 帖子异步审核，循环审核多个
     * @author: ZBoHang
     * @time: 2021/10/15 15:20
     */
    @Async("CustomAsyncThreadPoolExecutor")
    public void auditPosts(List<QueueMsg> list) {
        list.forEach(queueMsg -> this.auditPostQueue.send(queueMsg.getMsgContent(), queueMsg.getMsgCorrelationData()));
    }

    @Async("CustomAsyncThreadPoolExecutor")
    public void auditComment(Comment comment) {

    }
}
