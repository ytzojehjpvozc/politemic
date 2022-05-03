package com.xbh.politemic.biz.notice.controller;

import com.xbh.politemic.biz.notice.srv.NoticeSrv;
import com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @NoticeController: 通知/私信 ctrl
 * @author: ZBoHang
 * @time: 2021/12/13 16:09
 */
@RestController
@RequestMapping("/user/notices")
public class NoticeController {

    @Autowired
    private NoticeSrv noticeSrv;

    /**
     * 获取未读 通知/私信 个数
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2021/12/13 16:33
     */
    @GetMapping("getUnReadNoticeCnt")
    public Result getUnReadNoticeCnt() {

        String userId = ThreadLocalUtils.getUserId();

        ApiAssert.noneBlank(userId, "未登录不能获取未读通知个数!");

        return Result.success(this.noticeSrv.getUnReadNoticeCnt(userId));
    }

    /**
     * 获取 通知/私信 详情
     * @param noticeId 通知/私信id
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2021/12/13 16:39
     */
    @GetMapping("getNoticeDetail/{noticeId}")
    public Result getNoticeDetail(@PathVariable(name = "noticeId", required = false) String noticeId) {

        ApiAssert.noneBlank(noticeId, "noticeId参数不能为空!");

        String userId = ThreadLocalUtils.getUserId();

        ApiAssert.noneBlank(noticeId, "未登录不能获取通知详情!");

        GetNoticeDetailResponseVO vo = this.noticeSrv.getNoticeDetail(noticeId, userId);

        return Result.success(vo);
    }

}
