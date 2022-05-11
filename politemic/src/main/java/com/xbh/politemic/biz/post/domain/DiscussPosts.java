package com.xbh.politemic.biz.post.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class DiscussPosts implements Serializable {
    /**
     * 帖子id
     */
    @Id
    private String id;

    /**
     * 帖子所属用户id
     */
    private String userId;

    /**
     * 帖子主题
     */
    private String title;

    /**
     * 帖子类型 1-普通 2-置顶
     */
    private String type;

    /**
     * 帖子状态
      1-发表后待审核
      2-正常
      3-精华帖
      4-管理删除的拉黑帖
     */
    private String status;

    /**
     * 帖子创建时间
     */
    private Date createTime;

    /**
     * 帖子点赞数
     */
    private Integer starCount;

    /**
     * 帖子评论数
     */
    private Integer commentCount;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子公开性 1-公开 2-私密 仅自己可见 默认公开
     */
    private String confessed;

    private static final long serialVersionUID = 1L;
}