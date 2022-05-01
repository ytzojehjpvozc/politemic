package com.xbh.politemic.handler;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.log.domain.ExceptionLog;
import com.xbh.politemic.biz.log.dto.LogDTO;
import com.xbh.politemic.biz.log.srv.BaseExceptionLogSrv;
import com.xbh.politemic.common.exception.ApiException;
import com.xbh.politemic.common.exception.ServiceException;
import com.xbh.politemic.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    private final String EXCEPTIONS_HANDLER = "请求发生错误,异常序号: {} ,请联系管理员处理";

    @Autowired
    private BaseExceptionLogSrv baseExceptionLogSrv;

    @ExceptionHandler(ApiException.class)
    public Result apiExceptionHandler(ApiException ex) {
        return Result.failure(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(ServiceException.class)
    public Result srvExceptionHandler(ServiceException ex) {
        return Result.failure(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result commonExceptionHandler(Exception ex) {
        // 获取堆栈信息
        String trace = this.getTrace(ex);
        // 构建一个异常日志
        ExceptionLog exceptionLog = LogDTO.buildExceptionLog(trace);
        // 持久化
        Integer id = this.baseExceptionLogSrv.insertUseGeneratedKeys(exceptionLog);
        // 返回给客户端错误id
        return Result.failure(StrUtil.format(this.EXCEPTIONS_HANDLER, id));
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
