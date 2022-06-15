package com.xbh.politemic.common.enums.user;

import lombok.Getter;

/**
 * @FollowStatusEnum: 关注状态枚举
 * @author: ZBoHang
 * @time: 2022/1/6 16:22
 */
@Getter
public enum FollowStatusEnum {

    EFFECTIVE("1", "有效的"),
    INVALID("2", "无效的");

    private String code;
    private String define;

    private FollowStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }
}
