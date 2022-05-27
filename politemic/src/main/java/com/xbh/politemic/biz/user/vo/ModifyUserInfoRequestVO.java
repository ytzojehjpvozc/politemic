package com.xbh.politemic.biz.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ModifyUserInfoRequestVO: 修改用户信息 请求 vo
 * @author: ZBoHang
 * @time: 2021/12/23 10:09
 */
@Data
@Accessors(chain = true)
@ApiModel
public class ModifyUserInfoRequestVO implements Serializable {

    private static final long serialVersionUID = -1769515365423600630L;

    /**
     * 旧密码
     */
    @ApiModelProperty("旧密码")
    private String oldUserPass;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    private String newUserPass;

    /**
     * 绑定邮箱
     */
    @ApiModelProperty("绑定邮箱")
    private String email;

    /**
     * 评论尾巴
     */
    @ApiModelProperty("评论尾巴")
    private String tail;

    /**
     * 尾巴开启状态 Y-开启 N-关闭 默认关闭
     */
    @ApiModelProperty("尾巴开启状态 Y-开启 N-关闭 默认关闭")
    private String tailStatus;
}
