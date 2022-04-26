package com.xbh.politemic.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.biz.user.mapper.UserTokenMapper;
import com.xbh.politemic.common.annotation.NoneNeedLogin;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.ThreadLocalUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

/**
 * @AuthInterceptor: 系统权限拦截
 * @author: ZBoHang
 * @time: 2021/10/9 14:07
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthInterceptor.class);

    private static final String MDC_KEY = "USER_ID";
    @Autowired
    RedisClient redisClient;
    @Resource
    UserTokenMapper userTokenMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (handler instanceof HandlerMethod) {
            // 获取请求对应控制器上的NoneNeedLogin注解
            NoneNeedLogin noneNeedLogin = ((HandlerMethod) handler).getMethodAnnotation(NoneNeedLogin.class);
            if (noneNeedLogin == null) {
                // 拿到请求中的令牌
                String token = ServletUtil.getHeaderIgnoreCase(request, Constants.TOKEN);
                if (StrUtil.isBlank(token)) {
                    this.responseMsg(response, Result.noneAuth().toJsonString());
                    return Boolean.FALSE;
                }
                // 查询redis中是否存在
                String realTokenKey = Constants.USER_TOKEN_PRE + token;
                if (redisClient.hasKey(realTokenKey)) {
                    // 存在则比对token的值,相等则放行
                    String userId = redisClient.get(realTokenKey);
                    // 设置日志标志
                    this.setMDC(userId);
                    // 设置用户id至本地线程副本中
                    ThreadLocalUtils.setUserId(userId);
                    return Boolean.TRUE;
                } else {
                    UserToken userToken = userTokenMapper.selectOne(new UserToken().setToken(token));
                    // 数据库中能查到且到期时间在当前时间之后 则放行
                    if (userToken != null && userToken.getExpire().after(new Date(System.currentTimeMillis()))) {
                        // 设置日志标志
                        this.setMDC(userToken.getUserId());
                        // 设置用户id至本地线程副本中
                        ThreadLocalUtils.setUserId(userToken.getUserId());
                        return Boolean.TRUE;
                    }
                }
                // 不存在则拦截 让用户去登录
                this.responseMsg(response, Result.noneAuth().toJsonString());
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
        // return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // MDC、ThreadLocal等在此销毁,清除释放资源
        MDC.clear();
        ThreadLocalUtils.remove();
        // super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 响应json数据
     * @author: ZBoHang
     * @time: 2021/10/12 9:56
     */
    private void responseMsg(HttpServletResponse response, String result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(result);
        } catch (IOException e) {
            log.error("@@@@@拦截器向前端响应数据异常");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 轻量级日志的唯一标志
     * @author: ZBoHang
     * @time: 2021/10/12 10:02
     */
    private void setMDC(String userId) {
        MDC.put(MDC_KEY, userId);
    }
}
