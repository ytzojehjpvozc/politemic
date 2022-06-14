package com.xbh.politemic.biz.user.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.common.constant.UserConstant;

/**
 * @TokenBuilder: token build
 * @author: ZBoHang
 * @time: 2022/1/6 15:38
 */
public class TokenBuilder {

    /**
     * 构建一个新的token
     * @author: ZBoHang
     * @time: 2021/12/10 9:10
     */
    public static UserToken buildNewToken(String userId, String token) {

        UserToken userToken = null;

        if (StrUtil.isAllNotBlank(userId, token)) {

            userToken = new UserToken().setUserId(userId)

                    .setToken(token)

                    .setExpire(DateUtil.date(System.currentTimeMillis() + UserConstant.TOKEN_TIME_OUT_IN_DB));


        }

        return userToken;
    }

    /**
     * 构建一个带有用户id 的 userToken
     * @param userId 用户id
     * @return: com.xbh.politemic.biz.user.domain.UserToken
     * @author: ZBoHang
     * @time: 2022/1/6 15:50
     */
    public static UserToken buildWithUserId(String userId) {

        UserToken userToken = null;

        if (StrUtil.isNotBlank(userId)) {

            userToken = new UserToken()

                    .setUserId(userId);
        }

        return userToken;
    }

    /**
     * 构建一个带有token 的 userToken
     * @param token 用户令牌
     * @return: com.xbh.politemic.biz.user.domain.UserToken
     * @author: ZBoHang
     * @time: 2022/1/6 15:53
     */
    public static UserToken buildWithToken(String token) {

        UserToken userToken = null;

        if (StrUtil.isNotBlank(token)) {

            userToken = new UserToken()

                    .setToken(token);
        }

        return userToken;
    }
}
