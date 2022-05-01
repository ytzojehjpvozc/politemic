package com.xbh.politemic.biz.post.dto;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.post.domain.DiscussPosts;

/**
 * @PostDTO: 讨论帖数据传输转换
 * @author: ZBoHang
 * @time: 2021/12/10 16:34
 */
public class PostDTO {

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
}
