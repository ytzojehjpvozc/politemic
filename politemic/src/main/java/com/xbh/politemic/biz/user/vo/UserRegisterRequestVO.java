package com.xbh.politemic.biz.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @UserRegisterRequestVO: 用户注册请求 VO
 * @author: ZBoHang
 * @time: 2021/12/10 9:59
 */
@Data
@Accessors(chain = true)
public class UserRegisterRequestVO implements Serializable {

    private static final long serialVersionUID = -3843080726244593241L;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 绑定邮箱
     */
    private String email;
}
