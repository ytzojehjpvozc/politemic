package com.xbh.politemic.biz.post.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.builder.CommentBuilder;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.vo.PublishCommentRequestVO;
import com.xbh.politemic.common.enums.post.CommentTypeEnum;
import com.xbh.politemic.common.enums.post.PostConfessionEnum;
import com.xbh.politemic.common.enums.post.PostStatusEnum;
import com.xbh.politemic.common.util.ServiceAssert;
import com.xbh.politemic.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @CommentSrv: 评论 srv
 * @author: ZBoHang
 * @time: 2021/12/16 16:10
 */
@Service
public class CommentSrv extends BaseCommentSrv {

    @Autowired
    private BasePostSrv basePostSrv;
    @Autowired
    private AsyncTask asyncTask;

    /**
     * 发布评论去审核
     * @param vo vo
     * @param userId 用户id
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2021/12/20 17:25
     */
    @Transactional(rollbackFor = Exception.class)
    public String publishComment(PublishCommentRequestVO vo, String userId) {

        String type = CommentTypeEnum.getTypeByStr(vo.getType());

        String targetId = vo.getTargetId();
        // 评论类型若为对帖子的评论
        if (StrUtil.equals(CommentTypeEnum.TARGET_POSTS.getCode(), type)) {

            DiscussPosts dp = this.basePostSrv.selectByPrimaryKey(targetId);

            ServiceAssert.isFalse(dp == null || StrUtil.equals(PostConfessionEnum.PRIVACY.getCode(), dp.getConfessed())
                    // 帖子状态不能是未审核或者已删除
                    || StrUtil.equalsAny(dp.getStatus(), PostStatusEnum.PENDING_REVIEW.getCode(), PostStatusEnum.SHIELD.getCode()), "未找见讨论帖!");
        } else {
            // 评论类型为 对评论的评论
            Comment comment = this.selectByPrimaryKey(targetId);

            ServiceAssert.notNull(comment, "未找见评论!");
        }
        // 构建一个初始状态的评论
        Comment comment = CommentBuilder.buildInitComment(vo, userId, type);
        // 保存
        this.insert(comment);
        // 异步 去审核评论内容
        this.asyncTask.auditComment(comment);

        return "评论发表成功,等待审核，审核结果会以通知的形式告知!";
    }
}
