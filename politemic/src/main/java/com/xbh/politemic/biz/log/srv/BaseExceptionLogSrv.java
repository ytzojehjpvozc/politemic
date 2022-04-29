package com.xbh.politemic.biz.log.srv;

import com.xbh.politemic.biz.log.domain.ExceptionLog;
import com.xbh.politemic.biz.log.mapper.ExceptionLogMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseExceptionLogSrv: base 异常日志 srv
 * @author: ZBoHang
 * @time: 2021/12/9 16:47
 */
@Service
public class BaseExceptionLogSrv extends CommonSrv<ExceptionLog> {

    @Resource
    protected ExceptionLogMapper exceptionLogMapper;
}
