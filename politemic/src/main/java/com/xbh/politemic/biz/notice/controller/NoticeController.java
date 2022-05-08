package com.xbh.politemic.biz.notice.controller;

import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xbh.politemic.biz.notice.srv.NoticeSrv;
import com.xbh.politemic.biz.notice.vo.PageNoticeRequestVO;
import com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api("通知/私信 模块")
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
    @ApiOperation("未读 通知/私信 个数")
    @GetMapping("getUnReadNoticeCnt")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "获取未读通知/私信个数", remark = "没有就是0")
    public Result getUnReadNoticeCnt() {

        String userId = ThreadLocalUtil.getUserId();

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
    @ApiOperation("通知/私信 详情")
    @ApiImplicitParam(name = "noticeId", value = "通知id", paramType = "path")
    @GetMapping("getNoticeDetail/{noticeId}")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "获取通知/私信详情", remark = "有权限校验")
    public Result getNoticeDetail(@PathVariable(name = "noticeId", required = false) String noticeId) {

        ApiAssert.noneBlank(noticeId, "noticeId参数不能为空!");
        // 用户id
        String userId = ThreadLocalUtil.getUserId();
        // 用户令牌
        String token = ThreadLocalUtil.getToken();

        ApiAssert.isTrue(StrUtil.isAllNotBlank(userId, token), "未登录不能获取通知详情!");
        // 获取 通知/私信 详情
        GetNoticeDetailResponseVO vo = this.noticeSrv.getNoticeDetail(noticeId, userId, token);

        return Result.success(vo);
    }

    /**
     * 通知/私信 分页
     * @param :
     * @author: zhengbohang
     * @date: 2021/12/13 20:19
     */
    @ApiOperation("通知/私信 分页")
    @ApiOperationSupport(ignoreParameters = {"data", "totalPageSize", "totalResultSize"})
    @GetMapping("pageNotice")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "通知/私信分页", remark = "分页逻辑在于业务层,默认未读通知")
    public Result pageNotice(PageNoticeRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空!");
        // 用户id
        String userId = ThreadLocalUtil.getUserId();
        // 用户令牌
        String token = ThreadLocalUtil.getToken();

        ApiAssert.isTrue(StrUtil.isAllNotBlank(userId, token), "未登录不能分页获取通知!");

        return Result.success(this.noticeSrv.pageNotice(vo, userId, token));
    }

}
