package com.xbh.politemic.biz.log.dto;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.log.domain.ExceptionLog;
import com.xbh.politemic.biz.log.domain.SysLog;

import java.sql.Timestamp;

/**
 * @LogDTO: 日志模块数据传输转换
 * @author: ZBoHang
 * @time: 2021/12/10 9:40
 */
public class LogDTO {

    /**
     * 构建一条请求日志记录
     * @param userId 用户id
     * @param requestURI 请求地址
     * @param params 参数
     * @param ipAddress ip地址
     * @param modelName 模块名
     * @param behavior 模块功能
     * @param remark 备注
     * @author: ZBoHang
     * @time: 2021/12/10 9:46
     */
    public static SysLog buildSysLog(String userId, String requestURI, String params,
                                     String ipAddress, String modelName, String behavior, String remark) {

        SysLog sysLog = null;

        if (StrUtil.isAllNotBlank(ipAddress, modelName, behavior, remark)) {

            sysLog = new SysLog()
                    // 用户id
                    .setUserId(userId)
                    // 请求地址
                    .setPath(requestURI)
                    // 参数
                    .setParams(params)
                    // 模块名
                    .setModelName(modelName)
                    // 模块功能
                    .setBehavior(behavior)
                    // 备注
                    .setRemark(remark)
                    // ip地址
                    .setIp(ipAddress)
                    // 请求时间
                    .setTime(new Timestamp(System.currentTimeMillis()));
        }
        return sysLog;
    }

    /**
     * 构建一个异常日志
     * @param trace 异常堆栈
     * @return: com.xbh.politemic.biz.log.domain.ExceptionLog
     * @author: ZBoHang
     * @time: 2021/12/10 16:49
     */
    public static ExceptionLog buildExceptionLog(String trace) {

        ExceptionLog exceptionLog = null;

        if (StrUtil.isNotBlank(trace)) {
            // 异常堆栈
            exceptionLog = new ExceptionLog().setTrace(trace)
                    // 日期
                    .setDatetime(new Timestamp(System.currentTimeMillis()));
        }
        return exceptionLog;
    }
}
