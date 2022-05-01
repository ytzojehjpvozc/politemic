package com.xbh.politemic.task;

import com.xbh.politemic.biz.queue.domain.QueueMsg;
import com.xbh.politemic.biz.queue.srv.BaseQueueSrv;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.common.constant.QueueConstant;
import com.xbh.politemic.common.imapper.ScheduleTaskMapper;
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
 * @DynamicScheduleTakeTail: 对于失败的https请求, 定时去api重新获取尾巴
 * @author: ZBoHang
 * @time: 2021/10/14 17:24
 */
@Configuration
@EnableScheduling
public class DynamicScheduleTakeTail implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicScheduleTakeTail.class);

    /**
     * 定时获取尾巴任务配置的配置名
     */
    private final String TAKE_TAIL_SCHEDULE_TASK_CONFIG_NAME = "schedule_task_take_tail";
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
    @Autowired
    private BaseQueueSrv baseQueueSrv;
    @Autowired
    private AsyncTask asyncTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {

            log.info("@@@@@定时任务:推送获取尾巴的失败消息>>>>>>>>>>>>>>>启动");

            Example example = Example.builder(QueueMsg.class).build();

            Example.Criteria criteria = example.createCriteria();
            // 队列消息表中消息类型 0-邮件消息 1-获取用户评论尾巴消息 2-帖子审核消息
            criteria.andEqualTo(QueueConstant.MSG_TYPE_COLUMN_NAME, Constants.STATUS_STR_ONE)

                    .andNotEqualTo(this.STATUS_K, this.STATUS_V)

                    .andGreaterThan(this.CREATE_TIME_K, new Timestamp(System.currentTimeMillis() - this.ONE_DAY));
            // 查找
            List<QueueMsg> list = this.baseQueueSrv.selectByExample(example);
            // 如果错误消息
            if (!list.isEmpty()) {
                // TODO: 2021/10/15 尾巴后续可能会修改,则需要对比用户是否已经修改过尾巴再进行操作
                // 异步去执行
                this.asyncTask.getTails(list);
            }
        }, triggerContext -> {
            // 拿到任务名称
            String scheduleTaskName = this.environment.getProperty(this.TAKE_TAIL_SCHEDULE_TASK_CONFIG_NAME);
            // 拿到cron表达式
            String cronStr = this.scheduleTaskMapper.getCron(scheduleTaskName);
            // 配置
            return new CronTrigger(cronStr).nextExecutionTime(triggerContext);
        });
    }
}
