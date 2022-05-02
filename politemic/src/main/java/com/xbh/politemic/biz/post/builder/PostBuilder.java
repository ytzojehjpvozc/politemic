package com.xbh.politemic.biz.post.builder;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.common.enums.post.PostStatusEnum;
import com.xbh.politemic.common.enums.post.PostTypeEnum;
import com.xbh.politemic.common.util.StrKit;

import java.sql.Timestamp;

/**
 * @PostDTO: 讨论帖数据传输转换
 * @author: ZBoHang
 * @time: 2021/12/10 16:34
 */
public class PostBuilder {

    /**
     * 构建一个带有审核状态的讨论帖
     * @param postId 帖子id
     * @param postAuditStatus 帖子审核状态
     * @return: com.xbh.politemic.biz.post.domain.DiscussPosts
     * @author: ZBoHang
     * @time: 2021/12/10 16:37
     */
    public static DiscussPosts buildPostWithAuditStatus(String postId, String postAuditStatus) {

        DiscussPosts discussPosts = null;

        if (StrUtil.isAllNotBlank(postId, postAuditStatus)) {
            // 帖子id
            discussPosts = new DiscussPosts().setId(postId)
                    // 帖子审核状态
                    .setStatus(postAuditStatus);
        }

        return discussPosts;
    }

    /**
     * 构建一个初始化帖子
     * @param title 帖子主题
     * @param content 帖子内容
     * @param userId 用户id
     * @author: ZBoHang
     * @time: 2021/12/13 14:19
     */
    public static DiscussPosts buildInitiallyPost(String title, String content, String userId) {

        DiscussPosts discussPosts = null;

        if (StrUtil.isAllNotBlank(title, content, userId)) {
            // 生成帖子id
            String postId = StrKit.getUUID();
            // 初始化帖子信息
            discussPosts = new DiscussPosts().setId(postId)
                    // 用户id
                    .setUserId(userId)
                    // 创建时间
                    .setCreateTime(new Timestamp(System.currentTimeMillis()))
                    // 新建帖子初始状态  1-发表后待审核  2-正常  3-精华帖  4-管理删除、审核未通过的拉黑帖
                    .setStatus(PostStatusEnum.PENDING_REVIEW.getCode())
                    // 帖子类型
                    .setType(PostTypeEnum.ORDINARY.getCode());
        }

        return discussPosts;
    }
}
