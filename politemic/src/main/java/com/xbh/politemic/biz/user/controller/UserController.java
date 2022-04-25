package com.xbh.politemic.biz.user.controller;

import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用户控制器
 * @Author: zhengbohang
 * @Date: 2021/10/3 13:45
 */
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserSrv userSrv;

    @NoneNeedLogin
    @PostMapping("test")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "用于测试", remark = "test可选")
    public Result test(@RequestParam(name = "test") String test) throws Exception {
        return Result.success("123321");
    }

    @NoneNeedLogin
    @PostMapping("userLogin")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "用户登录", remark = "日志中密码替换")
    public Result userLogin(@RequestParam(name = "userName", required = false) String userName,
                            @RequestParam(name = "userPass", required = false) String userPass) {
        return this.userSrv.doLogin(userName, userPass);
    }

    @NoneNeedLogin
    @PostMapping("userRegister")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "用户注册", remark = "只允许普通用户进行注册")
    public Result userRegister(@RequestBody(required = false) SysUser sysUser) {
        return this.userSrv.register(sysUser);
    }

    @NoneNeedLogin
    @GetMapping("userActivate/{id}/{activateCode}")
    @SysLog(modelName = Constants.USER_MODEL_NAME, behavior = "激活账户", remark = "普通用户激活")
    public Result userActivate(@PathVariable(name = "id", required = false) String id,
                               @PathVariable(name = "activateCode", required = false) String activateCode) {
        return this.userSrv.activate(id,activateCode);
    }

}
