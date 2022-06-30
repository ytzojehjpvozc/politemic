package com.xbh.politemic.biz.user.controller;

import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.biz.user.vo.ModifyUserInfoRequestVO;
import com.xbh.politemic.biz.user.vo.UserLoginRequestVO;
import com.xbh.politemic.biz.user.vo.UserRegisterRequestVO;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import io.swagger.annotations.*;
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

    @ApiImplicitParam(name = "test",value = "测试参数",required = true, dataTypeClass = String.class)
    @ApiOperation(value = "测试接口")
    @PostMapping("test")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "用于测试", remark = "remark")
    public Result test(@RequestParam(name = "test") String test) throws Exception {
        if ("1".equals(test)) {
            throw new RuntimeException("123");
        }
        String solvedData = Optional.ofNullable(test).orElse("空的test参数");
        return Result.success(solvedData);
    }

    @NoneNeedLogin
    @ApiOperation(value = "用户登录接口")
    @PostMapping("userLogin")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "用户登录", remark = "日志中密码替换")
    public Result userLogin(@ApiParam UserLoginRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空!");

        ApiAssert.noneBlank(vo.getUserName(), "用户名不能为空!");

        ApiAssert.noneBlank( vo.getUserPass(), "密码不能为空!");

        return Result.success(this.userSrv.doLogin(vo));
    }

    @NoneNeedLogin
    @ApiOperation(value = "用户注册接口")
    @PostMapping("userRegister")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "用户注册", remark = "只允许普通用户进行注册")
    public Result userRegister(@ApiParam @RequestBody(required = false) UserRegisterRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空");

        ApiAssert.noneBlank(vo.getUserName(), "用户名不能为空!");

        ApiAssert.noneBlank(vo.getUserPass(), "密码不能为空!");

        ApiAssert.noneBlank(vo.getEmail(), "邮箱不能为空!");

        return Result.success(this.userSrv.register(vo));
    }

    @NoneNeedLogin
    @ApiOperation(value = "用户激活接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", dataTypeClass = String.class),
            @ApiImplicitParam(name = "activateCode", value = "激活码", paramType = "path", dataTypeClass = String.class)
    })
    @GetMapping("userActivate/{id}/{activateCode}")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "激活账户", remark = "普通用户激活")
    public Result userActivate(@PathVariable(name = "id", required = false) String id,
                               @PathVariable(name = "activateCode", required = false) String activateCode) {

        ApiAssert.noneBlank(id, "请求参数id异常!");

        ApiAssert.noneBlank(activateCode, "请求参数activateCode异常");

        return Result.success(this.userSrv.activate(id,activateCode));
    }

    @ApiOperation(value = "获取用户信息接口")
    @GetMapping("getUserInfo")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "获取用户信息", remark = "需要token")
    public Result getUserInfo() {

        String token = ThreadLocalUtil.getToken();

        ApiAssert.noneBlank(token, "未登录不能获取用户信息!");

        return Result.success(this.userSrv.getUserInfo(token));
    }

    @ApiOperation("解除邮箱绑定")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "userPass", value = "用户密码", required = false, dataTypeClass = String.class)
            }
    )
    @PostMapping("unBindMailBox")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "解除邮箱绑定", remark = "解除邮箱绑定,重复解绑会报错")
    public Result unBindMailBox(String userPass) {

        ApiAssert.noneBlank(userPass, "解绑时密码不能为空!");

        String token = ThreadLocalUtil.getToken();

        return Result.success(this.userSrv.unBindMailBox(userPass, token));
    }

    @ApiOperation("修改用户信息")
    @PostMapping("modifyUserInfo")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "修改用户信息", remark = "修改用户信息 密码、头像、尾巴、尾巴状态")
    public Result modifyUserInfo(@RequestBody(required = false) ModifyUserInfoRequestVO vo) {

        ApiAssert.notNull(vo, "请求参数不能为空!");

        String token = ThreadLocalUtil.getToken();

        return Result.success(this.userSrv.modifyUserInfo(token, vo));
    }

    @ApiOperation("获赞数量")
    @GetMapping("myStar")
    @SysLog(modelName = CommonConstants.USER_MODEL_NAME, behavior = "我的获赞数量", remark = "需要登录")
    public Result myStar() {

        String token = ThreadLocalUtil.getToken();

        return Result.success(this.userSrv.myStar(token));
    }
}
