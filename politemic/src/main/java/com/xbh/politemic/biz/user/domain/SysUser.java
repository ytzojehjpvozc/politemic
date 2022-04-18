package com.xbh.politemic.biz.user.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 加盐密码
     */
    private String userPass;

    /**
     * 绑定邮箱
     */
    private String email;

    /**
     * 权限等级 0:超管 1:普通管理  2:普通用户
     */
    private String authFieldLevel;

    /**
     * 用户状态 0:未激活  1:已激活
     */
    private String status;

    /**
     * 发送到邮箱的验证码
     */
    private String activationCode;

    /**
     * 用户头像url
     */
    private String headerUrl;

    /**
     * 评论尾巴
     */
    private String tail;

    /**
     * 尾巴开启状态 Y-开启 N-关闭 默认关闭
     */
    private String tailStatus;

    private static final long serialVersionUID = 1L;
}