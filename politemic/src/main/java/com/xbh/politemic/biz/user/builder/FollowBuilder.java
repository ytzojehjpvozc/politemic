package com.xbh.politemic.biz.user.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.user.domain.Follow;
import com.xbh.politemic.common.enums.user.FollowStatusEnum;

/**
 * @FollowBuilder: follow build
 * @author: ZBoHang
 * @time: 2022/1/6 16:31
 */
public class FollowBuilder {

    /**
     * 构建相反状态的 follow
     * @param follow 关注关系
     * @return: com.xbh.politemic.biz.user.domain.Follow
     * @author: ZBoHang
     * @time: 2022/1/6 16:45
     */
    public static Follow buildOppositeStatus(Follow follow) {

        if (follow != null) {

            follow.setFollowTime(DateUtil.date());

            if (StrUtil.equals(FollowStatusEnum.EFFECTIVE.getCode(), follow.getStatus())) {

                follow.setStatus(FollowStatusEnum.INVALID.getCode());
            } else {

                follow.setStatus(FollowStatusEnum.EFFECTIVE.getCode());
            }
        }

        return follow;
    }

    /**
     * 构建一个有效关注
     * @param userId 关注用户
     * @param followedUserId 被关注用户
     * @return: com.xbh.politemic.biz.user.domain.Follow
     * @author: ZBoHang
     * @time: 2022/1/6 16:49
     */
    public static Follow build(String userId, String followedUserId) {

        Follow follow = null;

        if (StrUtil.isAllNotBlank(userId, followedUserId)) {

            follow = new Follow()

                    .setFollowUserId(userId)

                    .setFollowedUserId(followedUserId)

                    .setFollowTime(DateUtil.date())

                    .setStatus(FollowStatusEnum.EFFECTIVE.getCode());
        }

        return follow;
    }
}
