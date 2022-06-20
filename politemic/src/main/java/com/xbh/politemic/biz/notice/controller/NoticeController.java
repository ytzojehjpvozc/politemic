package com.xbh.politemic.biz.notice.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xbh.politemic.biz.notice.srv.NoticeSrv;
import com.xbh.politemic.biz.notice.vo.PageNoticeRequestVO;
import com.xbh.politemic.biz.notice.vo.SendLetterRequestVO;
import com.xbh.politemic.common.annotation.ApiIdempotent;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result getNoticeDetail(@PathVariable(name = "noticeId", required = false) Integer noticeId) {

        ApiAssert.isTrue(noticeId != null && noticeId > 0, "noticeId参数不能为空!");
        // 用户id
        String userId = ThreadLocalUtil.getUserId();
        // 用户令牌
        String token = ThreadLocalUtil.getToken();
        // 获取 通知/私信 详情 返回
        return Result.success(this.noticeSrv.getNoticeDetail(noticeId, userId, token));
    }

    /**
     * 通知/私信 分页
     * @param vo :
     * @author: zhengbohang
     * @date: 2021/12/13 20:19
     */
    @ApiOperation("通知/私信 分页")
    @ApiOperationSupport(ignoreParameters = {"data", "totalPageSize", "totalResultSize"})
    @GetMapping("pageNotice")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "通知/私信分页", remark = "分页逻辑在于业务层,默认未读通知")
    public Result pageNotice(@ApiParam PageNoticeRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空!");
        // 用户id
        String userId = ThreadLocalUtil.getUserId();

        return Result.success(this.noticeSrv.pageNotice(vo, userId));
    }

    /**
     * 用户之间发送私信
     * @param vo vo
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2022/1/17 9:40
     */
    @ApiOperation("发送私信")
    @ApiIdempotent(describe = "发送私信 限次时间3s", stintTime = 3L)
    @PostMapping("sendLetter")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "用户之间发送私信", remark = "需要登录 幂等性校验3s")
    public Result sendLetter(@ApiParam SendLetterRequestVO vo) {

        ApiAssert.notNull(vo, "未获取到请求参数!");

        ApiAssert.noneBlank(vo.getToId(), "未获取到私信目标用户!");

        ApiAssert.noneBlank(vo.getContent(), "未获取到私信内容!");

        String userId = ThreadLocalUtil.getUserId();

        return Result.success(this.noticeSrv.sendLetter(vo, userId));
    }

}
