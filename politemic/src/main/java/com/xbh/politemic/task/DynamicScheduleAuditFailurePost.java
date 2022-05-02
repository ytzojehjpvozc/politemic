package com.xbh.politemic.task;

import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.common.constant.QueueConstant;
import com.xbh.politemic.common.enums.queue.QueueMsgStatusEnum;
import com.xbh.politemic.common.enums.queue.QueueMsgTypeEnum;
import com.xbh.politemic.common.imapper.ScheduleTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @DynamicScheduleAuditFailurePost: 动态定时审核失败帖子
 * @author: ZBoHang
 * @time: 2021/12/13 14:39
 */
@Slf4j
@Configuration
@EnableScheduling
public class DynamicScheduleAuditFailurePost implements SchedulingConfigurer {

    /**
     * 自定义配置中发送失败邮件定时任务的配置名
     */
    private final String AUDIT_POST_SCHEDULE_TASK_CONFIG_NAME = "schedule_task_audit_failure_post";

    @Autowired
    private Environment environment;
    @Resource
    private ScheduleTaskMapper scheduleTaskMapper;
    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private AsyncTask asyncTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskReg) {

        taskReg.addTriggerTask(() -> {
            // 设置执行任务流程
            log.info("@@@@@定时任务:推送失败邮件>>>>>>>>>>>>>>>启动");
            // 状态不是2 且创建时间在一天前的这个时候之后
            Example example = Example.builder(QueueMsg.class).build();

            Example.Criteria criteria = example.createCriteria();

            criteria.andNotEqualTo(QueueConstant.MSG_STATUS_COLUMN_NAME, QueueMsgStatusEnum.MSG_CONSUMED.getCode())

                    .andGreaterThan(QueueConstant.MSG_CREATE_TIME_COLUMN_NAME, new Timestamp(System.currentTimeMillis() - QueueConstant.ONE_DAY))
                    // 队列消息表中消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息
                    .andEqualTo(QueueConstant.MSG_TYPE_COLUMN_NAME, QueueMsgTypeEnum.MSG_AUDIT_POST.getCode());

            List<QueueMsg> list = this.baseQueueSrv.selectByExample(example);

            if (list != null && !list.isEmpty()) {
                // 帖子异步审核，循环审核多个
                this.asyncTask.auditPosts(list);
            }

        }, triggerContext -> {
            // 设置任务周期
            // 从自定义配置中拿到配置名
            String scheduleTaskConfigName = environment.getProperty(AUDIT_POST_SCHEDULE_TASK_CONFIG_NAME);
            // 通过配置名拿到Cron表达式
            String cron = scheduleTaskMapper.getCron(scheduleTaskConfigName);
            // 返回定时任务的运行周期
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }
}
