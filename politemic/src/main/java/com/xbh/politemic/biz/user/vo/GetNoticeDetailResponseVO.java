package com.xbh.politemic.biz.user.vo;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.notice.domain.Notice;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @GetNoticeDetailResponseVO: 获取通知详情 responseVO
 * @author: ZBoHang
 * @time: 2021/12/13 16:49
 */
@Data
@Accessors(chain = true)
public class GetNoticeDetailResponseVO implements Serializable {
    private static final long serialVersionUID = 5660766737881367599L;

    /**
     * 通知序号
     */
    private Integer noticeId;

    /**
     * 通知方名称 不填为系统通知 有值则为私信(对应用户id)
     */
    private String fromName;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 时间
     */
    private Date time;

    /**
     * 构建 获取通知详情 responseVO
     * @param notice 详情
     * @param userName 通知/私信 方名称
     * @return: com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO
     * @author: ZBoHang
     * @time: 2021/12/13 17:04
     */
    public static GetNoticeDetailResponseVO build(Notice notice, String userName) {

        GetNoticeDetailResponseVO vo = null;

        if (notice !=null && StrUtil.isNotBlank(userName)) {

            vo = new GetNoticeDetailResponseVO()
                    // 序号
                    .setNoticeId(notice.getId())
                    // 通知/私信 方名称
                    .setFromName(userName)
                    // 通知/私信 内容
                    .setContent(notice.getContent())
                    // 通知/私信 时间
                    .setTime(notice.getTime());
        }

        return vo;
    }
}
