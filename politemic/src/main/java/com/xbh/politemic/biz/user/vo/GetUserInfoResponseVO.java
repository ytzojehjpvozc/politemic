package com.xbh.politemic.biz.user.vo;

import com.xbh.politemic.biz.user.domain.SysUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @GetUserInfoResponseVO: 获取用户信息 response vo
 * @author: ZBoHang
 * @time: 2021/12/14 11:44
 */
@Data
@Accessors(chain = true)
public class GetUserInfoResponseVO implements Serializable {

    private static final long serialVersionUID = -7969353886904315448L;

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 绑定邮箱
     */
    private String email;

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

    /**
     * 构建响应的用户信息
     * @param sysUser 用户信息
     * @return: com.xbh.politemic.biz.user.vo.GetUserInfoResponseVO
     * @author: ZBoHang
     * @time: 2021/12/14 11:50
     */
    public static GetUserInfoResponseVO build(SysUser sysUser) {

        GetUserInfoResponseVO vo = null;

        if (sysUser != null) {

            vo = new GetUserInfoResponseVO()
                    // id
                    .setId(sysUser.getId())
                    // 用户名
                    .setUserName(sysUser.getUserName())
                    // 邮箱
                    .setEmail(sysUser.getEmail())
                    // 头像url
                    .setHeaderUrl(sysUser.getHeaderUrl())
                    // 评论尾巴
                    .setTail(sysUser.getTail())
                    // 评论尾巴开启状态
                    .setTailStatus(sysUser.getTailStatus());
        }

        return vo;
    }
}
