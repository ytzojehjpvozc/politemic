package com.xbh.politemic.biz.notice.vo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.user.srv.UserSrv;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * @param noticeList 通知列表
     * @param fromName 来源方名称
     * @return: java.util.List<com.xbh.politemic.biz.notice.vo.PageNoticeResponseVO>
     * @author: ZBoHang
     * @time: 2021/12/14 14:46
     */
    public static List<PageNoticeResponseVO> buildSystemNotice(List<Notice> noticeList, String fromName) {

        List<PageNoticeResponseVO> voList = null;

        if (noticeList != null && !noticeList.isEmpty() && StrUtil.isNotBlank(fromName)) {

            voList = new ArrayList<>(noticeList.size());

            for (Notice notice : noticeList) {
                // 每次都需要创建对象
                PageNoticeResponseVO vo = new PageNoticeResponseVO();
                // id
                vo.setNoticeId(notice.getId())
                        // 来源方名称
                        .setFromName(fromName)
                        // 时间
                        .setTime(notice.getTime());

                voList.add(vo);
            }
        }

        return voList;
    }

    /**
     * 构建私信列表
     * @param noticeList 私信列表
     * @return: java.util.List<com.xbh.politemic.biz.notice.vo.PageNoticeResponseVO>
     * @author: ZBoHang
     * @time: 2021/12/14 15:19
     */
    public static List<PageNoticeResponseVO> buildUserLetter(List<Notice> noticeList) {

        List<PageNoticeResponseVO> voList = null;

        if (noticeList != null && !noticeList.isEmpty()) {

            voList = new ArrayList<>(noticeList.size());

            UserSrv userSrv = SpringUtil.getBean(UserSrv.class);

            for (Notice notice : noticeList) {
                // 每次都需要创建对象
                PageNoticeResponseVO vo = new PageNoticeResponseVO();
                // 获取私信来源方 名称
                String userName = userSrv.selectByPrimaryKey(notice.getFromId()).getUserName();
                // id
                vo.setNoticeId(notice.getId())
                        // 来源方名称
                        .setFromName(userName)
                        // 时间
                        .setTime(notice.getTime());

                voList.add(vo);
            }
        }

        return voList;
    }
}
