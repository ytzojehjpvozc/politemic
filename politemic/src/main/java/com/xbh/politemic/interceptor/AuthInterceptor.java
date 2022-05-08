package com.xbh.politemic.interceptor;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.HttpServletUtil;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @AuthInterceptor: 系统权限拦截
 * @author: ZBoHang
 * @time: 2021/10/9 14:07
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private UserSrv userSrv;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            // 获取请求对应控制器上的NoneNeedLogin注解
            NoneNeedLogin noneNeedLogin = ((HandlerMethod) handler).getMethodAnnotation(NoneNeedLogin.class);

            if (noneNeedLogin == null) {
                // 拿到请求中的令牌
                String token = HttpServletUtil.getUserToken(request);
                // 无令牌 拦截请求 返回权限异常
                if (StrUtil.isBlank(token)) {

                    HttpServletUtil.responseMsg(response, Result.noneAuth().toJsonString());
                    // 拦截
                    return Boolean.FALSE;
                }
                // 获取用户信息
                SysUser sysUser = this.userSrv.getUserInfoByToken(token);
                // 获取用户id
                String userId = sysUser.getId();
                // 设置日志标志 存用户令牌
                this.setMDC(userId);
                // 设置用户id至本地线程副本中
                ThreadLocalUtil.setUserId(userId);
                // 设置用户令牌至本地线程副本中
                ThreadLocalUtil.setToken(token);
            }
        }
        // 放行
        return Boolean.TRUE;
        // return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // MDC、ThreadLocal等在此销毁,清除释放资源
        MDC.clear();
        ThreadLocalUtil.removeAll();
        // super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 轻量级日志的唯一标志
     * @author: ZBoHang
     * @time: 2021/10/12 10:02
     */
    private void setMDC(String userId) {
        MDC.put(CommonConstants.MDC_KEY, userId);
    }
}
