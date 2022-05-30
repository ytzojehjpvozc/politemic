package com.xbh.politemic.biz.post.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.vo.PageGetPostsRequestVO;
import com.xbh.politemic.biz.post.vo.PulishPostRequestVO;
import com.xbh.politemic.common.enums.post.PostConfessionEnum;
import com.xbh.politemic.common.enums.post.PostStatusEnum;
import com.xbh.politemic.common.enums.post.PostTypeEnum;
import com.xbh.politemic.common.util.StrKit;

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
     * @param vo 请求参数
     *       title 帖子主题
     *       content 帖子内容
     *       confessed 帖子公开性
     * @param userId 用户id
     * @author: ZBoHang
     * @time: 2021/12/13 14:19
     */
    public static DiscussPosts buildInitiallyPost(PulishPostRequestVO vo, String userId) {

        String title = vo.getTitle();

        String content = vo.getContent();

        String confessed = vo.getConfessed();

        DiscussPosts discussPosts = null;

        if (StrUtil.isAllNotBlank(title, content, userId)) {
            // 生成帖子id
            String postId = StrKit.getUUID();
            // 初始化帖子信息
            discussPosts = new DiscussPosts().setId(postId)
                    // 用户id
                    .setUserId(userId)
                    // 帖子主题
                    .setTitle(title)
                    // 帖子内容
                    .setContent(content)
                    // 创建时间
                    .setCreateTime(DateUtil.date())
                    // 新建帖子初始状态  1-发表后待审核  2-正常  3-精华帖  4-管理删除、审核未通过的拉黑帖
                    .setStatus(PostStatusEnum.PENDING_REVIEW.getCode())
                    // 帖子类型
                    .setType(PostTypeEnum.ORDINARY.getCode())
                    // 帖子公开性 默认公开
                    .setConfessed(PostConfessionEnum.getConfessionByStr(confessed));
        }

        return discussPosts;
    }

    /**
     * 构建分页获取帖子列表的帖子实体
     * @param vo
     *      type
     *          帖子类型 1-普通 2-置顶
     *      status
     *          1-发表后待审核  2-正常  3-精华帖  4-管理删除的拉黑帖
     *      confessed
     *          帖子公开性 1-公开 2-私密 仅自己可见 默认公开
     *
     * @return: com.xbh.politemic.biz.post.domain.DiscussPosts
     * @author: ZBoHang
     * @time: 2021/12/15 11:38
     */
    public static DiscussPosts buildPageGetPosts(PageGetPostsRequestVO vo) {

        DiscussPosts discussPosts = null;

        if (vo != null) {
            // 类型
            String type = PostTypeEnum.getTypeByStr(vo.getType());
            // 状态
            String status = PostStatusEnum.getNormalStatusByStr(vo.getStatus());
            // 公开性
            String confessed = PostConfessionEnum.getConfessionByStr(vo.getConfessed());

            discussPosts = new DiscussPosts().setType(type)

                    .setStatus(status)

                    .setConfessed(confessed);
        }

        return discussPosts;
    }

    /**
     * 构建一个es文档对象
     * @param title 帖子主题
     * @param content 帖子内容
     * @return: com.xbh.politemic.biz.post.domain.DiscussPosts
     * @author: ZBoHang
     * @time: 2021/12/28 16:27
     */
    public static DiscussPosts buildESBO(String title, String content) {

        DiscussPosts bo = null;

        if (StrUtil.isAllNotBlank(title, content)) {

            bo = new DiscussPosts()

                    .setTitle(title)

                    .setContent(content)

                    .setCreateTime(DateUtil.date());
        }

        return bo;
    }
}
