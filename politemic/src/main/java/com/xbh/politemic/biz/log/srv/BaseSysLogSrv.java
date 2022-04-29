package com.xbh.politemic.biz.log.srv;

import com.xbh.politemic.biz.log.domain.SysLog;
import com.xbh.politemic.biz.log.mapper.SysLogMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseSysLogSrv: base 系统日志 srv
 * @author: ZBoHang
 * @time: 2021/12/9 16:55
 */
@Service
public class BaseSysLogSrv extends CommonSrv<SysLog> {

    @Resource
    protected SysLogMapper sysLogMapper;
}
