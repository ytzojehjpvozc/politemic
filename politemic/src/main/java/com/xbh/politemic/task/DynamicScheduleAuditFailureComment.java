package com.xbh.politemic.task;

import cn.hutool.core.date.DateUtil;
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

import java.util.List;

/**
 * @DynamicScheduleAuditFailureComment: 动态审核异常评论
 * @author: ZBoHang
 * @time: 2021/12/21 9:40
 */
@Slf4j
@Configuration
@EnableScheduling
public class DynamicScheduleAuditFailureComment implements SchedulingConfigurer {

    /**
     * 自定义配置中发送失败邮件定时任务的配置名
     */
    private final String AUDIT_COMMENT_SCHEDULE_TASK_CONFIG_NAME = "schedule_task_audit_failure_comment";

    @Autowired
    private Environment environment;
    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;
    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private AsyncTask asyncTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(() -> {
            // 设置任务流程
            log.info("@@@@@定时任务:推送失败评论>>>>>>>>>>>>>>>启动");

            Example example = Example.builder(QueueMsg.class).build();

            Example.Criteria criteria = example.createCriteria();
            // 状态不是被消费的
            criteria.andNotEqualTo(QueueConstant.MSG_STATUS_COLUMN_NAME, QueueMsgStatusEnum.MSG_CONSUMED)
                    // 消息生产时间在一天内
                    .andGreaterThan(QueueConstant.MSG_CREATE_TIME_COLUMN_NAME, DateUtil.date(System.currentTimeMillis() - QueueConstant.ONE_DAY))
                    // 类型为评论审核消息
                    .andEqualTo(QueueConstant.MSG_TYPE_COLUMN_NAME, QueueMsgTypeEnum.MSG_AUDIT_COMMENT.getCode());
            // 查询
            List<QueueMsg> list = this.baseQueueSrv.selectByExample(example);

            if (list != null && !list.isEmpty()) {
                // 不为空则去审核
                this.asyncTask.auditComments(list);
            }

        }, triggerContext -> {
            // 设置任务周期
            // 从自定义环境配置中拿到任务配置名
            String taskConfigName = this.environment.getProperty(AUDIT_COMMENT_SCHEDULE_TASK_CONFIG_NAME);
            // 拿到cron表达式
            String cron = this.scheduleTaskMapper.getCron(taskConfigName);
            // 返回定时任务的运行周期
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }
}
