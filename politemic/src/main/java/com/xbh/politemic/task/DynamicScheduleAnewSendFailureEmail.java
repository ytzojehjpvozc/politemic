package com.xbh.politemic.task;

import com.xbh.politemic.common.imapper.ScheduleTaskMapper;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.mapper.QueueMsgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @DynamicScheduleTask: 动态定时任务发送失败邮件
 * @author: ZBoHang
 * @time: 2021/10/12 13:11
 */
@Configuration
@EnableScheduling
public class DynamicScheduleAnewSendFailureEmail implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicScheduleAnewSendFailureEmail.class);
    /**
     * 自定义配置中发送失败邮件定时任务的配置名
     */
    private final String SEND_EMAIL_SCHEDULE_TASK_CONFIG_NAME = "schedule_task_send_failure_email";
    /**
     * 失败邮件筛选条件的字段
     */
    private final String STATUS_K = "status";
    private final String STATUS_V = "2";
    private final String CREATE_TIME_K = "productTime";
    private final long ONE_DAY = 1000 * 60 * 60 * 24;

    @Autowired
    private Environment environment;
    @Resource
    private ScheduleTaskMapper scheduleTaskMapper;
    @Resource
    private QueueMsgMapper queueMsgMapper;
    @Autowired
    private AsyncTask asyncTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskReg) {
        taskReg.addTriggerTask(() -> {
            // 设置执行任务流程
            log.info("@@@@@定时任务:推送失败邮件>>>>>>>>>>>>>>>启动");
            // 状态不是3 且创建时间在一天前的这个时候之后
            Example example = Example.builder(QueueMsg.class).build();
            Example.Criteria criteria = example.createCriteria();
            criteria.andNotEqualTo(this.STATUS_K, this.STATUS_V)
                    .andGreaterThan(this.CREATE_TIME_K, new Timestamp(System.currentTimeMillis() - this.ONE_DAY))
                    .andEqualTo(Constants.MSG_TYPE_COLUMN_NAME, Constants.MSG_TYPE_EMAIL);
            List<QueueMsg> list = this.queueMsgMapper.selectByExample(example);
            if (list != null && !list.isEmpty()) {
                this.asyncTask.createActivateEmailMsgs(list);
            }
        }, triggerContext -> {
            // 设置任务周期
            // 从自定义配置中拿到配置名
            String scheduleTaskConfigName = this.environment.getProperty(this.SEND_EMAIL_SCHEDULE_TASK_CONFIG_NAME);
            // 通过配置名拿到Cron表达式
            String cron = this.scheduleTaskMapper.getCron(scheduleTaskConfigName);
            // 返回定时任务的运行周期
            return new CronTrigger(cron).nextExecutionTime(triggerContext);
        });
    }
}
