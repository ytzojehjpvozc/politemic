package com.xbh.politemic.biz.user.controller;

import com.xbh.politemic.biz.user.srv.ViewSrv;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ViewController: 访问量 ctrl
 * @author: ZBoHang
 * @time: 2022/1/17 14:07
 */
@Api("访问量模块")
@RestController
@RequestMapping("/admin/view")
public class ViewController {

    @Autowired
    private ViewSrv viewSrv;

    /**
     * 获取当天用户访问量
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2022/1/17 14:12
     */
    @NoneNeedLogin
    @ApiOperation("当天访问量")
    @GetMapping("getTodayViews")
    @SysLog(modelName = CommonConstants.ADMIN_MODEL_NAME, behavior = "获取当天用户访问量", remark = "管理人员才能访问")
    public Result getTodayViews() {

        return Result.success(this.viewSrv.getTodayViews());
    }
}
