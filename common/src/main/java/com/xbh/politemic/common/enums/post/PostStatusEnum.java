package com.xbh.politemic.common.enums.post;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public enum PostStatusEnum {

    PENDING_REVIEW("1", "发表后待审核"),
    NORMAL("2", "正常"),
    ESSENCE("3", "精华帖"),
    SHIELD("4", "管理删除、审核未通过的拉黑帖");

    private final String code;
    private final String define;

    private PostStatusEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

    /**
     * 获取正常的帖子状态
     * @param str str
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/15 11:56
     */
    public static String getNormalStatusByStr(String str) {

        if (StrUtil.isNotBlank(str)) {

            for (PostStatusEnum value : values()) {

                if (StrUtil.equals(value.getCode(), str)) {

                    return value.getCode();
                }
            }
        }
        // 默认正常状态
        return NORMAL.getCode();
    }
}
