package com.xbh.politemic.biz.notice.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.common.enums.notice.NoticeStatusEnum;

/**
 * @NoticeBuilder: 通知 builder
 * @author: ZBoHang
 * @time: 2021/12/13 14:24
 */
public class NoticeBuilder {

    /**
     * 构建一个未读的系统通知
     * @param userId 推送用户id
     * @return: com.xbh.politemic.biz.notice.domain.Notice
     * @author: ZBoHang
     * @time: 2021/12/13 14:29
     */
    public static Notice buildUnreadStatusNotice(String userId) {

        Notice notice = null;

        if (StrUtil.isNotBlank(userId)) {

            notice = new Notice()
                    // 通知状态 0-未读 1-已读 2-删除
                    .setStatus(NoticeStatusEnum.UNREAD_STATUS.getCode())
                    // 通知生成时间
                    .setTime(DateUtil.date())
                    // 推送用户id
                    .setToId(userId);
        }

        return notice;
    }

    /**
     * 构建一个已读状态的通知
     * @param noticeId 通知id
     * @return: com.xbh.politemic.biz.notice.domain.Notice
     * @author: ZBoHang
     * @time: 2021/12/15 10:11
     */
    public static Notice buildReadStatusNotice(Integer noticeId) {

        Notice notice = null;

        if (noticeId != null && noticeId > 0) {
            // 通知id
            notice = new Notice().setId(noticeId)
                    // 通知状态 0-未读 1-已读 2-删除
                    .setStatus(NoticeStatusEnum.READ_STATUS.getCode());
        }

        return notice;
    }

    /**
     * 构建一个未读的 带有内容的 通知
     * @param followedUserId 瑞松用户id
     * @param content 通知内容
     * @return: com.xbh.politemic.biz.notice.domain.Notice
     * @author: ZBoHang
     * @time: 2022/1/6 17:16
     */
    public static Notice buildUnReadStatusWithContentNotice(String followedUserId, String content) {

        Notice notice = null;

        if (StrUtil.isNotBlank(followedUserId)) {

            notice = new Notice()
                    // 通知状态 0-未读 1-已读 2-删除
                    .setStatus(NoticeStatusEnum.UNREAD_STATUS.getCode())
                    // 通知生成时间
                    .setTime(DateUtil.date())
                    // 推送用户id
                    .setToId(followedUserId)
                    // 通知内容
                    .setContent(content);
        }

        return notice;
    }
}
