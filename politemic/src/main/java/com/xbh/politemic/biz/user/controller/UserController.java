package com.xbh.politemic.biz.user.controller;

import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Description: 用户控制器
 * @Author: zhengbohang
 * @Date: 2021/10/3 13:45
 */
@Api("用户模块")
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserSrv userSrv;

    @NoneNeedLogin
    @ApiImplicitParam(name = "test",value = "测试参数",required = true)
    @ApiOperation(value = "测试接口")
    @PostMapping("test")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "用于测试", remark = "remark")
    public Result test(@RequestParam(name = "test") String test) throws Exception {
        String solvedData = Optional.ofNullable(test).orElse("空的test参数");
        return Result.success(solvedData);
    }

    @NoneNeedLogin
    @ApiImplicitParams({
                    @ApiImplicitParam(name = "userName", value = "用户名", required = false),
                    @ApiImplicitParam(name = "userPass", value = "密码", required = false)
    })
    @ApiOperation(value = "用户登录接口")
    @PostMapping("userLogin")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "用户登录", remark = "日志中密码替换")
    public Result userLogin(@RequestParam(name = "userName", required = false) String userName,
                            @RequestParam(name = "userPass", required = false) String userPass) {
        return this.userSrv.doLogin(userName, userPass);
    }

    @NoneNeedLogin
    @ApiOperation(value = "用户注册接口")
    @PostMapping("userRegister")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "用户注册", remark = "只允许普通用户进行注册")
    public Result userRegister(@RequestBody(required = false) SysUser sysUser) {
        return this.userSrv.register(sysUser);
    }

    @NoneNeedLogin
    @ApiOperation(value = "用户激活接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "path"),
            @ApiImplicitParam(name = "activateCode", value = "激活码", paramType = "path")
    })
    @GetMapping("userActivate/{id}/{activateCode}")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "激活账户", remark = "普通用户激活")
    public Result userActivate(@PathVariable(name = "id", required = false) String id,
                               @PathVariable(name = "activateCode", required = false) String activateCode) {
        return this.userSrv.activate(id,activateCode);
    }

}
