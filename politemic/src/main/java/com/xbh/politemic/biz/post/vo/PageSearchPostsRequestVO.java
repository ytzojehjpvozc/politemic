package com.xbh.politemic.biz.post.vo;

import com.xbh.politemic.common.util.PageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @PageSearchPostsRequestVO: 查询帖子 分页请求 vo
 * @author: ZBoHang
 * @time: 2022/1/5 10:28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel
public class PageSearchPostsRequestVO extends PageUtil implements Serializable {

    private static final long serialVersionUID = 3798703245799361981L;

    /**
     * 要搜索的指定关键词
     */
    @ApiModelProperty("要搜索的指定关键词")
    private String key;
}
