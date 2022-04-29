package com.xbh.politemic.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.biz.log.srv.BaseSysLogSrv;
import com.xbh.politemic.common.annotation.SysLog;
import com.xbh.politemic.common.constant.Constants;
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
import java.sql.Timestamp;
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
    @Autowired
    RedisClient redisClient;

    @Pointcut("@annotation(com.xbh.politemic.common.annotation.SysLog)")
    public void declaratPointCut() {

    }

    @After("declaratPointCut()")
    public void after(JoinPoint jp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取请求中的对应信息
        String userToken = ServletUtil.getHeaderIgnoreCase(request, Constants.TOKEN);
        String userId = redisClient.get(Constants.USER_TOKEN_PRE + userToken);
        String ipAddress = ServletUtil.getClientIP(request);
        String requestURI = request.getRequestURI();
        String params = this.getParams(request, requestURI);
        // 拿到方法上面的注解 以便拿到其中的内容
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        SysLog sysLogAnno = method.getAnnotation(SysLog.class);
        // 包装日志
        com.xbh.politemic.biz.log.domain.SysLog sysLog = new com.xbh.politemic.biz.log.domain.SysLog()
                .setUserId(userId)
                .setPath(requestURI)
                .setParams(params)
                .setModelName(sysLogAnno.modelName())
                .setBehavior(sysLogAnno.behavior())
                .setRemark(sysLogAnno.remark())
                .setIp(ipAddress).setTime(new Timestamp(System.currentTimeMillis()));
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

            if (requestURI.endsWith("Login")) {
                requestParamsMap.replace("userPass", this.ARR_REPLACE_PASSWORD);
            }
            return JSONObject.toJSONString(requestParamsMap);
        }
        return JSONObject.toJSONString(Collections.emptyMap());
    }
}
