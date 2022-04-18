package com.xbh.politemic.common.imapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ScheduleTask: 定时任务查询接口
 * @author: ZBoHang
 * @time: 2021/10/14 17:16
 */
@Mapper
public interface ScheduleTaskMapper {
    @Select("select cron from schedule_task where task_name = #{taskName} and status = 'Y'")
    String getCron(String taskName);
}
