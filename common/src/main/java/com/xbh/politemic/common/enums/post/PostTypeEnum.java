package com.xbh.politemic.common.enums.post;

import lombok.Getter;

@Getter
public enum PostTypeEnum {

    ORDINARY("1", "普通帖子"),
    TOP("2", "置顶帖子");

    private final String code;
    private final String define;

    private PostTypeEnum(String code, String define) {
        this.code = code;
        this.define = define;
    }

}
