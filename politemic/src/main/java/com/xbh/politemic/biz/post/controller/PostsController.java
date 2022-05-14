package com.xbh.politemic.biz.post.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xbh.politemic.biz.post.srv.PostSrv;
import com.xbh.politemic.biz.post.vo.PageGetPostsRequestVO;
import com.xbh.politemic.biz.post.vo.PulishPostRequestVO;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @PostsController: 帖子对应控制器
 * @author: ZBoHang
 * @time: 2021/10/15 14:04
 */
@Api("帖子模块")
@RestController
@RequestMapping("/user/posts")
public class PostsController {

    @Autowired
    private PostSrv postSrv;

    /**
     * 发布帖子
     * @param vo vo
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2021/12/15 16:53
     */
    @ApiOperation(value = "发布帖子接口")
    @PostMapping("publishPosts")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "发布帖子", remark = "发布完事审核,结果会以系统通知方式传递给用户")
    public Result publishPosts(PulishPostRequestVO vo) {

        ApiAssert.noneBlank(vo.getTitle(), "帖子主题不能为空!");

        ApiAssert.noneBlank(vo.getContent(), "帖子内容不能为空!");

        String userId = ThreadLocalUtil.getUserId();

        return Result.success(this.postSrv.publishPost(vo, userId));
    }

    /**
     * 分页查询(获取)帖子
     * @param vo
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2021/12/15 16:53
     */
    @ApiOperation("分页查询帖子")
    @ApiOperationSupport(ignoreParameters = {"data", "totalPageSize", "totalResultSize"})
    @NoneNeedLogin
    @GetMapping("pageGetPosts")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "分页获取帖子", remark = "有类型、状态、帖子公开性的筛选")
    public Result pageGetPosts(PageGetPostsRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空!");

        String token = ThreadLocalUtil.getToken();

        return Result.success(this.postSrv.pageGetPosts(vo, token));
    }

    /**
     * 获取帖子详情
     * @param postId 帖子id
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2021/12/15 18:04
     */
    @ApiOperation("获取帖子详情")
    @ApiImplicitParam(name = "postId", value = "帖子id", paramType = "path")
    @NoneNeedLogin
    @GetMapping("getPostDetail/{postId}")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "获取帖子详情", remark = "有权限校验")
    public Result getPostDetail(@PathVariable(name = "postId", required = false) String postId) {

        ApiAssert.noneBlank(postId, "postId不能为空!");

        String token = ThreadLocalUtil.getToken();

        return Result.success(this.postSrv.getPostDetail(postId, token));
    }
}
