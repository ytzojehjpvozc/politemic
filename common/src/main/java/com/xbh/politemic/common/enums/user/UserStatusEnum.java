package com.xbh.politemic.common.enums.user;

import lombok.Getter;

/**
 * @UserStatusEnum: 用户状态枚举
 * @author: ZBoHang
 * @time: 2021/12/13 13:08
 */
@Getter
public enum UserStatusEnum {

    INACTIVATED("0", "未激活"),
    ACTIVATED("1", "已激活");

    private final String code;
    private final String define;

    private UserStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }
}
