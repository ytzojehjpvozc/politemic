package com.xbh.politemic.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.xbh.politemic.biz.log.builder.LogBuilder;
import com.xbh.politemic.biz.log.domain.SysLog;
import com.xbh.politemic.biz.log.srv.BaseSysLogSrv;
import com.xbh.politemic.common.constant.UserConstant;
import com.xbh.politemic.common.util.ThreadLocalUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

/**
 * @Description: 系统日志拦截
 * @Author: zhengbohang
 * @Date: 2021/10/3 17:41
 */
@Aspect
@Component
public class LogInterceptor {

    /**
     * 用于替换日志中的明文密码
     */
    public final String ARR_REPLACE_PASSWORD = "******";

    @Autowired
    private BaseSysLogSrv baseSysLogSrv;

    @Pointcut("@annotation(com.xbh.politemic.common.annotation.SysLog)")
    public void declaratPointCut() {

    }

    @After("declaratPointCut()")
    public void after(JoinPoint jp) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取请求中的对应信息
        String userToken = ServletUtil.getHeaderIgnoreCase(request, UserConstant.TOKEN);
        // 用户id
        String userId = ThreadLocalUtils.getUserId();
        // IP地址
        String ipAddress = ServletUtil.getClientIP(request);
        // 请求url
        String requestURI = request.getRequestURI();
        // 请求参数
        String params = this.getParams(request, requestURI);
        // 拿到方法上面的注解 以便拿到其中的内容
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        com.xbh.politemic.common.annotation.SysLog sysLogAnno = method.getAnnotation(com.xbh.politemic.common.annotation.SysLog.class);
        // 构建日志
        SysLog sysLog = LogBuilder.buildSysLog(userId, requestURI, params, ipAddress,

                sysLogAnno.modelName(), sysLogAnno.behavior(), sysLogAnno.remark());

        // 保存
        this.baseSysLogSrv.insertSelective(sysLog);
    }

    /**
     * @description:
     * @param request: 获取请求参数 对密码进行替换
     * @author: zhengbohang
     * @date: 2021/10/3 19:15
     */
    private String getParams(HttpServletRequest request, String requestURI) {

        Map<String, String> requestParamsMap = ServletUtil.getParamMap(request);
        // public HashMap(Map<? extends K, ? extends V> m)构造方法可能会报空指针
        if (!requestParamsMap.isEmpty()) {
            // 登陆相关接口替换密码
            if (requestURI.endsWith("Login")) {

                requestParamsMap.replace("userPass", this.ARR_REPLACE_PASSWORD);
            }

            return JSONObject.toJSONString(requestParamsMap);
        }
        return JSONObject.toJSONString(Collections.emptyMap());
    }
}
