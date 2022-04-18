package com.xbh.politemic.biz.log.mapper;

import com.xbh.politemic.biz.log.domain.ExceptionLog;
import com.xbh.politemic.common.imapper.CommonMapper;


public interface ExceptionLogMapper extends CommonMapper<ExceptionLog> {

    Integer insertBackPrimaryKey(ExceptionLog record);

}