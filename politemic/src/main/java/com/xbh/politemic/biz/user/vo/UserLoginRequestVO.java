package com.xbh.politemic.biz.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @UserLoginRequestVO: 用户登录请求VO
 * @author: ZBoHang
 * @time: 20ApiM9 17:46
 */
@Data
@Accessors(chain = true)
@ApiModel
public class UserLoginRequestVO implements Serializable {

    private static final long serialVersionUID = 2814197222782482702L;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String userPass;
}
