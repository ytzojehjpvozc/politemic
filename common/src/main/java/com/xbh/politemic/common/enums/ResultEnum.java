package com.xbh.politemic.common.enums;

public enum ResultEnum {

    SUCCESS(200, "请求成功"),
    FAILURE(500, "服务器内部异常"),
    PARAMS_NONE_PASS(400, "参数校验未通过"),
    NONE_AUTH(401, "本次访问没有权限");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
