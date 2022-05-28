package com.xbh.politemic.biz.notice.vo;

import cn.hutool.extra.spring.SpringUtil;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.common.constant.NoticeConstant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @PageNoticeResponseVO: 分页获取 私信/通知 响应 VO
 * @author: ZBoHang
 * @time: 2021/12/14 13:33
 */
@Data
@Accessors(chain = true)
public class PageNoticeResponseVO implements Serializable {

    private static final long serialVersionUID = 4441488339427841178L;

    /**
     * 通知序号
     */
    private Integer noticeId;

    /**
     * 通知方名称 不填为系统通知 有值则为私信(对应用户id)
     */
    private String fromName;

    /**
     * 时间
     */
    private Date time;

    /**
     * 构建系统通知列表
     * @param notice 通知
     * @return: java.util.List<com.xbh.politemic.biz.notice.vo.PageNoticeResponseVO>
     * @author: ZBoHang
     * @time: 2021/12/14 14:46
     */
    public static PageNoticeResponseVO buildSystemNotice(Notice notice) {

        PageNoticeResponseVO vo = null;

        if (notice != null) {

            vo = new PageNoticeResponseVO()
                    // id
                    .setNoticeId(notice.getId())
                    // 来源方名称
                    .setFromName(NoticeConstant.SYSTEM_NOTICE_FROM_NAME)
                    // 时间
                    .setTime(notice.getTime());
        }

        return vo;
    }

    /**
     * 构建私信
     * @param notice 私信
     * @return: java.util.List<com.xbh.politemic.biz.notice.vo.PageNoticeResponseVO>
     * @author: ZBoHang
     * @time: 2021/12/14 15:19
     */
    public static PageNoticeResponseVO buildUserLetter(Notice notice) {

        PageNoticeResponseVO vo = null;

        if (notice != null) {

            vo = new PageNoticeResponseVO();

            UserSrv userSrv = SpringUtil.getBean(UserSrv.class);
            // 获取私信来源方 名称
            String userName = userSrv.selectByPrimaryKey(notice.getFromId()).getUserName();
            // id
            vo.setNoticeId(notice.getId())
                    // 来源方名称
                    .setFromName(userName)
                    // 时间
                    .setTime(notice.getTime());
        }

        return vo;
    }
}
