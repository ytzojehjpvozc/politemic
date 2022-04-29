package com.xbh.politemic.common.exception;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ApiException: 自定义api异常
 * @author: ZBoHang
 * @time: 2021/12/9 13:40
 */
@Data
@Accessors(chain = true)
public class ApiException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String msg;

    public ApiException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
