package com.xbh.politemic.biz.post.vo;

import com.xbh.politemic.common.util.PageUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @PageGetPostsRequestVO: 分页获取帖子 请求 vo
 * @author: ZBoHang
 * @time: 2021/12/15 10:53
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageGetPostsRequestVO extends PageUtil<Integer> implements Serializable {

    private static final long serialVersionUID = -4556826288312313420L;

    /**
     * 帖子类型 1-普通 2-置顶
     */
    private String type;

    /**
     * 1-发表后待审核  2-正常  3-精华帖  4-管理删除的拉黑帖
     */
    private String status;

    /**
     * 帖子公开性 1-公开 2-私密 仅自己可见 默认公开
     */
    private String confessed;

    /**
     * 默认  type-1-普通   status-2-正常 confessed-1-公开
     * 置顶帖  type-2-置顶   status-2-正常 confessed-1-公开
     * 精华帖  type-1-普通   status-3-精华帖 confessed-1-公开
     * 置顶加精帖  type-2-置顶   status-3-精华帖 confessed-1-公开
     * 我的帖子  type-不限 status-2或3 confessed-不限
     */
}
