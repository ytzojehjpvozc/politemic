package com.xbh.politemic.biz.user.controller;

import com.xbh.politemic.biz.user.srv.ViewSrv;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ApiAssert;
import com.xbh.politemic.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @ApiOperation("当天访问量")
    @GetMapping("getTodayViews")
    @SysLog(modelName = CommonConstants.ADMIN_MODEL_NAME, behavior = "获取当天用户访问量", remark = "管理人员才能访问")
    public Result getTodayViews() {

        return Result.success(this.viewSrv.getTodayViews());
    }

    /**
     * 时间区间内的访问量
     * @param startDateStr 开始日期字符串
     * @param endDateStr 结束日期字符串
     * @return: com.xbh.politemic.common.util.Result
     * @author: ZBoHang
     * @time: 2022/1/17 16:50
     */
    @ApiOperation("时间段内的访问量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDateStr", value = "开始日期字符串 yyyy-MM-dd", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "endDateStr", value = "结束日期字符串 yyyy-MM-dd", paramType = "query", dataTypeClass = String.class)
    })
    @PostMapping("getViewsBySegment")
    @SysLog(modelName = CommonConstants.ADMIN_MODEL_NAME, behavior = "获取一段时间区间的用户访问量", remark = "管理人员才能访问")
    public Result getViewsBySegment(String startDateStr, String endDateStr) {

        ApiAssert.noneBlank(startDateStr, "开始日期不能为空!");

        ApiAssert.noneBlank(endDateStr, "结束日期不能为空!");

        return Result.success(this.viewSrv.getViewsBySegment(startDateStr, endDateStr));
    }
}
