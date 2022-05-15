package com.xbh.politemic.biz.post.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @PublishCommentRequestVO: 发布评论请求 VO
 * @author: ZBoHang
 * @time: 2021/12/16 15:37
 */
@Data
@Accessors(chain = true)
@ApiModel
public class PublishCommentRequestVO implements Serializable {

    private static final long serialVersionUID = -466336274871432356L;

    /**
     * 评论类型 0-对帖子的评论 1-对评论的评论 默认 1-对评论的评论
     */
    @ApiModelProperty("评论类型 0-对帖子的评论 1-对评论的评论 默认 1-对评论的评论")
    private String type;

    /**
     * 目标id 对帖子的评论则为帖子id 对评论的评论则为评论id
     */
    @ApiModelProperty("目标id 对帖子的评论则为帖子id 对评论的评论则为评论id")
    private String targetId;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;
}
