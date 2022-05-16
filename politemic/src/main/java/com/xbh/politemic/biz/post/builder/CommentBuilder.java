package com.xbh.politemic.biz.post.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.vo.PublishCommentRequestVO;
import com.xbh.politemic.common.enums.post.CommentStatusEnum;
import com.xbh.politemic.common.util.StrKit;

/**
 * @CommentBuilder: 评论 builder
 * @author: ZBoHang
 * @time: 2021/12/16 16:27
 */
public class CommentBuilder {

    /**
     * 构建一个初始状态的评论
     * @param vo vo
     * @param userId 用户id
     * @param type 类型
     * @return: com.xbh.politemic.biz.post.domain.Comment
     * @author: ZBoHang
     * @time: 2021/12/20 17:08
     */
    public static Comment buildInitComment(PublishCommentRequestVO vo, String userId, String type) {

        Comment comment = null;

        if (vo != null) {

            String uuid = StrKit.getUUID();
            // 评论id
            comment = new Comment().setId(uuid)
                    // 评论 用户id
                    .setUserId(userId)
                    // 评论 目标id
                    .setTargetId(vo.getTargetId())
                    // 评论类型
                    .setType(type)
                    // 评论状态
                    .setStatus(CommentStatusEnum.PENDING_REVIEW.getCode())
                    // 评论创建时间
                    .setCreateTime(DateUtil.date())
                    // 评论内容
                    .setContent(vo.getContent());
        }

        return comment;

    }

    /**
     * 构建带有审核状态的评论
     * @param commentId 评论id
     * @param commentAuditStatus 评论状态
     * @return: com.xbh.politemic.biz.post.domain.Comment
     * @author: ZBoHang
     * @time: 2021/12/16 17:36
     */
    public static Comment buildCommentWithAuditStatus(String commentId, String commentAuditStatus) {

        Comment comment = null;

        if (StrUtil.isAllNotBlank(commentId, commentAuditStatus)) {

            comment = new Comment()
                    // 评论id
                    .setId(commentId)
                    // 评论状态
                    .setStatus(commentAuditStatus);
        }

        return comment;
    }
}
