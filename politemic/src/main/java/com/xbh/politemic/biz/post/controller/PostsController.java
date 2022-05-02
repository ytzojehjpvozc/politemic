package com.xbh.politemic.biz.post.controller;

import com.xbh.politemic.biz.post.srv.PostSrv;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "发布帖子接口")
    @PostMapping("publishPosts")
    public Result publishPosts(String title, String content) {

        ApiAssert.noneBlank(title, "帖子主题不能为空!");

        ApiAssert.noneBlank(content, "帖子内容不能为空!");

        String userId = ThreadLocalUtils.getUserId();

        ApiAssert.noneBlank(userId, "未登录不能发布帖子!");

        return Result.success(this.postSrv.publishPost(title, content, userId));
    }
}
