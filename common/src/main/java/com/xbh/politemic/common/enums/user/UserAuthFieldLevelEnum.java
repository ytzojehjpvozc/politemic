package com.xbh.politemic.common.enums.user;

import lombok.Getter;

/**
 * @UserAuthFieldLevelEnum: 用户权限等级枚举
 * @author: ZBoHang
 * @time: 2021/12/13 14:00
 */
@Getter
public enum UserAuthFieldLevelEnum {

    SUPER_ADMIN("0", "超管"),
    ADMIN("1", "普通管理"),
    USER("2", "普通用户");

    private final String code;
    private final String define;

    private UserAuthFieldLevelEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }
}
