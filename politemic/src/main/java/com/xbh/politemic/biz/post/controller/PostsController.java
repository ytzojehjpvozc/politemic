package com.xbh.politemic.biz.post.controller;

import com.xbh.politemic.biz.post.domain.DiscussPosts;
import com.xbh.politemic.biz.post.srv.PostSrv;
import com.xbh.politemic.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Result publishPosts(@RequestBody(required = false) DiscussPosts discussPosts) {
        return this.postSrv.publishPost(discussPosts);
    }
}
