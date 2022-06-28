package com.xbh.politemic.biz.post.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xbh.politemic.biz.post.srv.PostSrv;
import com.xbh.politemic.biz.post.vo.PageGetPostsRequestVO;
import com.xbh.politemic.biz.post.vo.PageSearchPostsRequestVO;
import com.xbh.politemic.biz.post.vo.PulishPostRequestVO;
import com.xbh.politemic.common.annotation.ApiIdempotent;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "发布帖子")
    @ApiIdempotent(describe = "同一请求发布帖子的时间限", stintTime = 10L)
    @PostMapping("publishPosts")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "发布帖子", remark = "发布完事审核,结果会以系统通知方式传递给用户")
    public Result publishPosts(@ApiParam PulishPostRequestVO vo) {

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
    public Result pageGetPosts(@ApiParam PageGetPostsRequestVO vo) {

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

    /**
     * 搜索帖子 会分词 需要登录
     * @param vo
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2022/1/5 14:31
     */
    @ApiOperation("搜索帖子")
    @ApiOperationSupport(ignoreParameters = {"data", "totalPageSize", "totalResultSize"})
    @ApiIdempotent(describe = "同一请求搜索帖子的时间限", stintTime = 30L)
    @PostMapping("searchPosts")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "搜索与指定关键词相关的帖子", remark = "搜索时会分词,且搜索必须要登录")
    public Result searchPosts(@ApiParam PageSearchPostsRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空!");

        return Result.success(this.postSrv.searchPosts(vo));
    }

    /**
     * 点赞/取消点赞 帖子
     * @param postId
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2022/2/8 10:43
     */
    @ApiOperation("点赞/取消点赞 帖子")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "postId", value = "帖子id", paramType = "query")
    )
    @PostMapping("likePost")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "点赞/取消点赞 指定的帖子", remark = "需登录")
    public Result likePost(String postId) {

        ApiAssert.noneBlank(postId, "未获取到帖子id!");

        String userId = ThreadLocalUtil.getUserId();

        return Result.success(this.postSrv.likePost(postId, userId));
    }
}
