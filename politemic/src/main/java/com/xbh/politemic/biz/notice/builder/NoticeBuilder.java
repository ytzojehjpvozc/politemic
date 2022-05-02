package com.xbh.politemic.biz.notice.builder;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.common.enums.notice.NoticeStatusEnum;

import java.sql.Timestamp;

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
                    .setTime(new Timestamp(System.currentTimeMillis()))
                    // 推送用户id
                    .setToId(userId);
        }

        return notice;
    }
}
