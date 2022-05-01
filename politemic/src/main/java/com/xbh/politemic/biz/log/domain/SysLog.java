package com.xbh.politemic.biz.log.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysLog implements Serializable {
    /**
     * 日志id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 请求用户的userId
     */
    private String userId;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 模块名
     */
    private String modelName;

    /**
     * 模块功能
     */
    private String behavior;

    /**
     * 模块备注
     */
    private String remark;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求时间
     */
    private Date time;

    /**
     * 请求参数
     */
    private String params;

    private static final long serialVersionUID = 1L;
}