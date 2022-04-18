package com.xbh.politemic.handler;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.log.domain.ExceptionLog;
import com.xbh.politemic.biz.log.mapper.ExceptionLogMapper;
import com.xbh.politemic.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.MessageFormat;

/**
 * @Description: 异常处理器
 * @Author: zhengbohang
 * @Date: 2021/10/3 10:32
 */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    /**
     * 异常处理 统一返回消息
     */
    private final String EXCEPTIONS_HANDLER = "请求发生错误,异常id: {id} ,请联系管理员处理";

    @Resource
    ExceptionLogMapper exceptionLogMapper;

    @ExceptionHandler(Exception.class)
    public Result handler(Exception ex) {
        // 获取堆栈信息
        String trace = this.getTrace(ex);
        // 持久化
        Integer id = this.saveException(trace);
        // 返回给客户端错误id
        return Result.failure(StrUtil.format(this.EXCEPTIONS_HANDLER, id));
    }

    /**
     * @description: 保存异常记录
     * @author: zhengbohang
     * @date: 2021/10/3 11:12
     */
    @Transactional(rollbackFor = Exception.class)
    Integer saveException(String traceMsg){
        ExceptionLog exceptionLog = new ExceptionLog();
        try {
            exceptionLogMapper.insertBackPrimaryKey(exceptionLog.setTrace(traceMsg)
                                                        .setDatetime(new Timestamp(System.currentTimeMillis())));
        } catch (Exception e) {
            log.info("@@@@@@请注意,异常日志保存失败");
        }
        return exceptionLog.getId();
    }

    /**
     * @description: 获取异常堆栈信息
     * @author: zhengbohang
     * @date: 2021/10/3 11:38
     */
    private String getTrace(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append(ex).append("\n");
        for (StackTraceElement el : stackTrace) {
            sb.append(MessageFormat.format("\tat {0}.{1}({2}:{3})\n",
                    el.getClassName(), el.getMethodName(), el.getFileName(), el.getLineNumber()));
        }
        return sb.toString();
    }
}
