package com.xbh.politemic.biz.post.vo;

import cn.hutool.extra.spring.SpringUtil;
import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.user.srv.UserSrv;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @PageGetPostsResponseVO: 分页获取帖子 响应 vo
 * @author: ZBoHang
 * @time: 2021/12/15 13:09
 */
@Data
@Accessors(chain = true)
public class PageGetPostsResponseVO implements Serializable {

    private static final long serialVersionUID = -612579705898651754L;

    /**
     * 帖子id
     */
    private String id;

    /**
     * 帖子所属用户名称
     */
    private String userName;

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
     * 帖子公开性 1-公开 2-私密 仅自己可见 默认公开
     */
    private String confessed;

    /**
     * 构建分页获取帖子列表响应对象
     * @param posts 原讨论帖
     * @return: java.util.List<com.xbh.politemic.biz.post.vo.PageGetPostsResponseVO>
     * @author: ZBoHang
     * @time: 2021/12/15 13:52
     */
    public static PageGetPostsResponseVO build(DiscussPosts posts) {

        PageGetPostsResponseVO vo = null;

        if (posts != null) {

            UserSrv userSrv = SpringUtil.getBean(UserSrv.class);
            // 获取发布用户名称
            String userName = userSrv.selectByPrimaryKey(posts.getUserId()).getUserName();

            vo = new PageGetPostsResponseVO()
                    // 序号
                    .setId(posts.getId())
                    // 所属用户名称
                    .setUserName(userName)
                    // 主题
                    .setTitle(posts.getTitle())
                    // 类型
                    .setType(posts.getType())
                    // 状态
                    .setStatus(posts.getStatus())
                    // 创建时间
                    .setCreateTime(posts.getCreateTime())
                    // 点赞数
                    .setStarCount(posts.getStarCount())
                    // 评论数
                    .setCommentCount(posts.getCommentCount())
                    // 公开性
                    .setConfessed(posts.getConfessed());
        }

        return vo;
    }
}
