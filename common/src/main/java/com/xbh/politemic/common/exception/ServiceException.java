package com.xbh.politemic.common.exception;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ServiceException: 自定义srv异常
 * @author: ZBoHang
 * @time: 2021/12/9 13:41
 */
@Data
@Accessors(chain = true)
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String msg;

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
