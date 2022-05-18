package com.xbh.politemic.biz.post.vo;

import cn.hutool.extra.spring.SpringUtil;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.user.srv.UserSrv;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @CommentResponseVO: 评论响应VO
 * @author: ZBoHang
 * @time: 2021/12/22 10:21
 */
@Data
@Accessors(chain = true)
public class CommentResponseVO implements Serializable {

    private static final long serialVersionUID = 254286339723939204L;

    /**
     * 评论id
     */
    private String id;

    /**
     * 发布评论的用户id
     */
    private String userName;

    /**
     * 目标id 对帖子的评论则为帖子id 对评论的评论则为评论id
     */
    private String targetId;

    /**
     * 评论发布时间
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 构建一个评论vo
     * @param comment 评论
     * @return: com.xbh.politemic.biz.post.vo.CommentResponseVO
     * @author: ZBoHang
     * @time: 2021/12/22 10:31
     */
    public static CommentResponseVO build(Comment comment) {

        CommentResponseVO vo = null;

        if (comment != null) {

            UserSrv userSrv = SpringUtil.getBean(UserSrv.class);
            // 获取发布用户名称
            String userName = userSrv.selectByPrimaryKey(comment.getUserId()).getUserName();

            vo = new CommentResponseVO()
                    // id
                    .setId(comment.getId())
                    // 发布评论人名称
                    .setUserName(userName)
                    // 目标id
                    .setTargetId(comment.getTargetId())
                    // 内容
                    .setContent(comment.getContent())
                    // 发布时间时间
                    .setCreateTime(comment.getCreateTime());
        }

        return vo;
    }
}
