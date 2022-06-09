package com.xbh.politemic.interceptor;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.common.annotation.ApiIdempotent;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ServiceAssert;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @IdempotentInterceptor: 幂等性校验 拦截
 * @author: ZBoHang
 * @time: 2022/1/4 17:22
 */
@Component
public class IdempotentInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取请求对应控制器上的NoneNeedLogin注解
            ApiIdempotent apiIdempotent = (handlerMethod).getMethodAnnotation(ApiIdempotent.class);
            // 方法上面有 幂等性校验注解
            if (apiIdempotent != null) {
                // 时间限制
                long stintTime = apiIdempotent.stintTime();
                //方法名
                String methodName = handlerMethod.getMethod().getName();
                // 请求令牌
                String token = ThreadLocalUtil.getToken();
                // key = pre + token + _ + method name
                String key = CommonConstants.REDIS_PRE_KEY_NAME_IDEMPOTENT + methodName + StrUtil.UNDERLINE + token;
                // 从redis中尝试拿 锁
                Boolean hasKeyFlag = this.redisClient.hasKey(key);
                // 有锁则拦截 报错
                ServiceAssert.isFalse(hasKeyFlag, "操作频繁,请稍后再试!");
                // 无锁先加锁
                this.redisClient.set(key, null, stintTime);
            }
        }
        // 放行
        return Boolean.TRUE;
        // return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // this.redisClient
        // super.postHandle(request, response, handler, modelAndView);
    }

}
