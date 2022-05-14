package com.xbh.politemic.biz.post.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.builder.CommentBuilder;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.vo.PublishCommentRequestVO;
import com.xbh.politemic.common.enums.post.CommentTypeEnum;
import com.xbh.politemic.common.enums.post.PostConfessionEnum;
import com.xbh.politemic.common.util.ServiceAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @CommentSrv: 评论 srv
 * @author: ZBoHang
 * @time: 2021/12/16 16:10
 */
@Service
public class CommentSrv extends BaseCommentSrv {

    @Autowired
    private BasePostSrv basePostSrv;

    public void publishComment(PublishCommentRequestVO vo, String userId) {

        String type = CommentTypeEnum.getTypeByStr(vo.getType());

        String targetId = vo.getTargetId();
        // 评论类型若为对帖子的评论
        if (StrUtil.equals(CommentTypeEnum.TARGET_POSTS.getCode(), type)) {

            DiscussPosts dp = this.basePostSrv.selectByPrimaryKey(targetId);

            ServiceAssert.isFalse(dp == null || StrUtil.equals(PostConfessionEnum.PRIVACY.getCode(), dp.getConfessed()), "未找见讨论帖!");
        } else {
            // 评论类型为 对评论的评论
            Comment comment = this.selectByPrimaryKey(targetId);

            ServiceAssert.notNull(comment, "未找见评论!");
        }

        Comment comment = CommentBuilder.buildInitComment(vo, userId, type);

        this.insert(comment);


    }
}
