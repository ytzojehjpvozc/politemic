package com.xbh.politemic.biz.post.controller;

import com.xbh.politemic.biz.post.srv.CommentSrv;
import com.xbh.politemic.biz.post.vo.PublishCommentRequestVO;
import com.xbh.politemic.common.annotation.ApiIdempotent;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @CommentController: 评论 ctrl
 * @author: ZBoHang
 * @time: 2021/12/16 14:54
 */
@Api("评论模块")
@RestController
@RequestMapping("/user/comment")
public class CommentController {

    @Autowired
    private CommentSrv commentSrv;

    @ApiOperation("发布评论")
    @ApiIdempotent(describe = "同一请求搜索帖子的时间限", stintTime = 7L)
    @PostMapping("publishComment")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "发布评论", remark = "发布完事审核,结果会以系统通知方式传递给用户")
    public Result publishComment(@ApiParam PublishCommentRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空");

        ApiAssert.noneBlank(vo.getTargetId(), "未获取到评论的目标!");

        ApiAssert.noneBlank(vo.getContent(), "未获取到评论的内容!");

        String userId = ThreadLocalUtil.getUserId();

        return Result.success(this.commentSrv.publishComment(vo, userId));
    }
}
