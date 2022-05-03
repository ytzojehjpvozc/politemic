package com.xbh.politemic.biz.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @UserLoginRequestVO: 用户登录请求VO
 * @author: ZBoHang
 * @time: 2021/12/9 17:46
 */
@Data
@Accessors(chain = true)
public class UserLoginRequestVO implements Serializable {

    private static final long serialVersionUID = 2814197222782482702L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPass;
}
