package com.xbh.politemic.biz.notice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @SendLetterRequestVO: 发送私信 请求 vo
 * @author: ZBoHang
 * @time: 2022/1/7 17:16
 */
@Data
@Accessors(chain = true)
@ApiModel
public class SendLetterRequestVO implements Serializable {

    private static final long serialVersionUID = 2336954245945802515L;

    /**
     * 接收方
     */
    @ApiModelProperty("接收方id")
    private String toId;

    /**
     * 通知内容
     */
    @ApiModelProperty("通知内容")
    private String content;
}
