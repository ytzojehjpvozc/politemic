package com.xbh.politemic.biz.user.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.notice.builder.NoticeBuilder;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.notice.srv.BaseNoticeSrv;
import com.xbh.politemic.biz.user.builder.FollowBuilder;
import com.xbh.politemic.biz.user.domain.Follow;
import com.xbh.politemic.common.enums.user.FollowStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @FollowSrv: 关注 srv
 * @author: ZBoHang
 * @time: 2022/1/6 15:36
 */
@Service
public class FollowSrv extends BaseFollowSrv {

    @Autowired
    private BaseUserSrv baseUserSrv;
    @Autowired
    private BaseNoticeSrv baseNoticeSrv;

    /**
     * 关注用户
     * @param userId 关注的用户
     * @param followedUserId 被关注的用户
     * @return: java.lang.String
     * @author: ZBoHang
     * @time: 2022/1/6 16:55
     */
    @Transactional(rollbackFor = Exception.class)
    public String followUser(String userId, String followedUserId) {

        Follow follow = this.getFollow(userId, followedUserId);
        // 有记录
        if (follow != null) {
            // 有记录 但是 无效
            if (StrUtil.equals(FollowStatusEnum.INVALID.getCode(), follow.getStatus())) {
                // 关注指定用户
                this.updateByPrimaryKey(FollowBuilder.buildOppositeStatus(follow));

                return "关注成功!";
            }
            // 取消关注指定用户
            this.updateByPrimaryKey(FollowBuilder.buildOppositeStatus(follow));

            return "取消成功!";
        }
        // 仅当第一次关注时 系统会发送通知
        String noticeContentTemplate = "用户:{}关注了你!";

        String userName = this.baseUserSrv.selectByPrimaryKey(userId).getUserName();

        String content = StrUtil.format(noticeContentTemplate, userName);
        // 构建一个未读的 带有内容的 通知
        Notice notice = NoticeBuilder.buildUnReadStatusWithContentNotice(followedUserId, content);
        // 发送给用户
        this.baseNoticeSrv.insertSelective(notice);
        // 无记录 代表 未关注 则关注指定用户
        this.insert(FollowBuilder.build(userId, followedUserId));

        return "关注成功!";
    }

    /**
     * 通过用户id 查找他们的关注关系
     * @param followUserId 关注用户id
     * @param followedUserId 被关注用户id
     * @return: com.xbh.politemic.biz.user.domain.Follow
     * @author: ZBoHang
     * @time: 2022/1/6 16:20
     */
    public Follow getFollow(String followUserId, String followedUserId) {

        Example example = Example.builder(Follow.class).build();

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("followUserId", followUserId)

                .andEqualTo("followedUserId", followedUserId);

        return this.selectOneByExample(example);
    }
}
