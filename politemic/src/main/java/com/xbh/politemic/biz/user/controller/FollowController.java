package com.xbh.politemic.biz.user.controller;

import com.xbh.politemic.biz.user.srv.FollowSrv;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FollowController: 关注 ctrl
 * @author: ZBoHang
 * @time: 2022/1/6 16:01
 */
@Api("关注模块")
@RestController
@RequestMapping("/user/follow")
public class FollowController {

    @Autowired
    private FollowSrv followSrv;

    @ApiOperation("关注/取消关注 用户")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "followedUserId", value = "被关注的用户id", paramType = "query")
    )
    @PostMapping("followUser")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "关注或取消关注 用户", remark = "要求用户必须登录才能关注别人")
    public Result followUser(String followedUserId) {

        ApiAssert.noneBlank(followedUserId, "未获取到被关注的用户id!");

        String userId = ThreadLocalUtil.getUserId();

        return Result.success(this.followSrv.followUser(userId, followedUserId));
    }
}
