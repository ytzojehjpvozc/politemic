package com.xbh.politemic.common.enums.user;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @UserTailStatusEnum: 用户评论尾巴状态 枚举
 * @author: ZBoHang
 * @time: 2021/12/23 10:43
 */
@Getter
public enum UserTailStatusEnum {

    TURN_ON("Y", "开启"),
    TURN_OFF("N", "关闭");

    private final String code;
    private final String define;

    private UserTailStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取尾巴状态 默认N-关闭
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/23 10:47
     */
    public static String getTailStatusByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (UserTailStatusEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }

        return TURN_OFF.getCode();
    }
}
