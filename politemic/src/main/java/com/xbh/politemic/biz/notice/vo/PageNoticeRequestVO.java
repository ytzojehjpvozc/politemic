package com.xbh.politemic.biz.notice.vo;

import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.common.util.PageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 分页 通知/私信 请求 vo
 * @Author: zhengbohang
 * @Date: 2021/12/13 20:20
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class PageNoticeRequestVO extends PageUtil<Notice> implements Serializable {

    private static final long serialVersionUID = -875928466775029562L;
    /**
     * 通知/私信 状态  0-未读 1-已读 2-删除
     */
    @ApiModelProperty("通知/私信 状态  0-未读 1-已读")
    private String status;

    /**
     * 通知/私信 类型 notice-通知 letter-私信
     */
    @ApiModelProperty("通知/私信 类型 notice-通知 letter-私信")
    private String type;

}
