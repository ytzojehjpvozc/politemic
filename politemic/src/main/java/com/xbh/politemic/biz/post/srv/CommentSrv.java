package com.xbh.politemic.biz.post.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.builder.CommentBuilder;
import com.xbh.politemic.biz.post.domain.Comment;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.vo.CommentResponseVO;
import com.xbh.politemic.biz.post.vo.PublishCommentRequestVO;
import com.xbh.politemic.common.enums.post.CommentStatusEnum;
import com.xbh.politemic.common.enums.post.CommentTypeEnum;
import com.xbh.politemic.common.enums.post.PostConfessionEnum;
import com.xbh.politemic.common.enums.post.PostStatusEnum;
import com.xbh.politemic.common.util.ServiceAssert;
import com.xbh.politemic.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

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
            // 评论状态必须正常
            ServiceAssert.isTrue(comment != null && StrUtil.equals(CommentStatusEnum.NORMAL.getCode(), comment.getStatus()), "未找见评论!");
        }
        // 构建一个初始状态的评论
        Comment comment = CommentBuilder.buildInitComment(vo, userId, type);
        // 保存
        this.insert(comment);
        // 异步 去审核评论内容
        this.asyncTask.auditComment(comment);

        return "评论发表成功,等待审核，审核结果会以通知的形式告知!";
    }

    /**
     * 通过帖子id 获取帖子所有评论
     * @param postId 帖子id
     * @author: ZBoHang
     * @time: 2021/12/21 11:23
     * @return
     */
    public List<Map<String, Object>> getCommentsByPostId(String postId) {

        Example example = Example.builder(Comment.class).build();

        example.setOrderByClause("create_time ASC");

        Example.Criteria criteria = example.createCriteria();
        // 类型为对帖子的评论
        criteria.andEqualTo("type", CommentTypeEnum.TARGET_POSTS.getCode())
                // 评论目标的id
                .andEqualTo("targetId", postId)
                // 状态为正常的评论
                .andEqualTo("status", CommentStatusEnum.NORMAL.getCode());
        // 对帖子的评论集合
        List<Comment> commentsForPost = this.selectByExample(example);
        // 转换评论响应vo
        List<CommentResponseVO> voForPostList = commentsForPost.stream().map(CommentResponseVO::build).collect(Collectors.toList());

        List<Map<String, Object>> resultList = new ArrayList<>(commentsForPost.size());
        // 循环遍历每一条对帖子的评论
        for (CommentResponseVO vo : voForPostList) {
            // 查询每一条对帖子评论 的 评论
            List<Comment> commentsForComment = this.getCommentsByCommentId(vo.getId());
            // 转换评论响应vo
            List<CommentResponseVO> voForComment = commentsForComment.stream().map(CommentResponseVO::build).collect(Collectors.toList());

            resultList.add(Collections.unmodifiableMap(new HashMap<String, Object>() {
                {
                    put("main", vo);
                    put("secondary", voForComment);
                }
            }));
        }

        return resultList;
    }

    /**
     * 通过评论id 获取所有针对于该评论的评论
     * @param commentId 评论id
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/21 11:43
     */
    public List<Comment> getCommentsByCommentId(String commentId) {

        Example example = Example.builder(Comment.class).build();

        example.setOrderByClause("create_time DESC");

        Example.Criteria criteria = example.createCriteria();
        // 类型为对评论的评论
        criteria.andEqualTo("type", CommentTypeEnum.TARGET_COMMENT.getCode())
                // 目标id为评论id
                .andEqualTo("targetId", commentId)
                // 状态正常
                .andEqualTo("status", CommentStatusEnum.NORMAL.getCode());

        return this.selectByExample(example);
    }
}
