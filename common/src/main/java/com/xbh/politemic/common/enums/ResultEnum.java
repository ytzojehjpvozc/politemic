package com.xbh.politemic.common.enums;

public enum ResultEnum {

    SUCCESS("200", "成功"),
    FAILURE("500", "失败"),
    NONE_AUTH("401", "没有权限");

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
