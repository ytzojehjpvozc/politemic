package com.xbh.politemic.biz.log.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@Table(name = "sys_exception_log")
public class ExceptionLog implements Serializable {
    /**
     * 异常日志的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 异常抛出时间
     */
    private Date datetime;

    /**
     * 异常堆栈信息
     */
    private String trace;

    private static final long serialVersionUID = 1L;
}