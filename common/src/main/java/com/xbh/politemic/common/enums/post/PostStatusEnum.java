package com.xbh.politemic.common.enums.post;

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
}
