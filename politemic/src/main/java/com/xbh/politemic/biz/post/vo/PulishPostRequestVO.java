package com.xbh.politemic.biz.post.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @PulishPostRequestVO: 发布帖子请求 vo
 * @author: ZBoHang
 * @time: 2021/12/15 9:27
 */
@Data
@Accessors(chain = true)
@ApiModel
public class PulishPostRequestVO implements Serializable {

    private static final long serialVersionUID = 5999889818973676948L;

    /**
     * 帖子主题
     */
    @ApiModelProperty("帖子主题")
    private String title;

    /**
     * 帖子内容
     */
    @ApiModelProperty("帖子内容")
    private String content;

    /**
     * 帖子公开性 1-公开 2-私密 仅自己可见 默认公开
     */
    @ApiModelProperty("帖子公开性 1-公开 2-私密 仅自己可见 默认公开")
    private String confessed;
}
