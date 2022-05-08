package com.xbh.politemic.common.util;

import cn.hutool.json.JSONUtil;
import com.xbh.politemic.common.enums.ResultEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Description: ctrl统一返回
 * @Author: zhengbohang
 * @Date: 2021/10/3 10:43
 */
@Getter
public class Result implements Serializable {

    private static final long serialVersionUID = -6166789405232241629L;

    private Integer code;
    private String msg;
    private Object bean;

    private Result() {
    }

    public static Result success() {
        Result result = new Result();
        result.code = ResultEnum.SUCCESS.getCode();
        result.msg = ResultEnum.SUCCESS.getMsg();
        return result;
    }

    public static Result success(String message) {
        Result result = new Result();
        result.code = ResultEnum.SUCCESS.getCode();
        result.msg = message;
        return result;
    }

    public static Result success(Object obj) {
        Result result = new Result();
        result.code = ResultEnum.SUCCESS.getCode();
        result.msg = ResultEnum.SUCCESS.getMsg();
        result.bean = obj;
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.code = ResultEnum.FAILURE.getCode();
        result.msg = ResultEnum.FAILURE.getMsg();
        return result;
    }

    public static Result failure(String message) {
        Result result = new Result();
        result.code = ResultEnum.FAILURE.getCode();
        result.msg = message;
        return result;
    }

    public static Result failure(Integer code, String msg) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }

    public static Result noneAuth() {
        Result result = new Result();
        result.code = ResultEnum.NONE_AUTH.getCode();
        result.msg = ResultEnum.NONE_AUTH.getMsg();
        return result;
    }

    public String toJsonString() {
        return JSONUtil.toJsonStr(this);
    }
}
