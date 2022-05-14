package com.xbh.politemic.biz.post.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Comment implements Serializable {
    /**
     * 评论id
     */
    @Id
    private String id;

    /**
     * 发布评论的用户id
     */
    private String userId;

    /**
     * 评论类型 0-对帖子的评论 1-对评论的评论
     */
    private String type;

    /**
     * 目标id 对帖子的评论则为帖子id 对评论的评论则为评论id
     */
    private String targetId;

    /**
     * 评论状态 0-发布等待审核 1-正常 2-删除 3-禁用
     */
    private String status;

    /**
     * 评论发布时间
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

}