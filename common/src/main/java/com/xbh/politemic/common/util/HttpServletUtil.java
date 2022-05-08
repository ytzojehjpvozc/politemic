package com.xbh.politemic.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.constant.UserConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @HttpServletUtil: http servlet 工具类
 * @author: ZBoHang
 * @time: 2021/12/14 9:36
 */
public class HttpServletUtil extends ServletUtil {

    private HttpServletUtil() {
    }

    /**
     * 获取用户token
     * @author: ZBoHang
     * @time: 2021/12/14 9:52
     */
    public static String getUserToken(HttpServletRequest request) {

        return getHeaderIgnoreCase(request, UserConstant.TOKEN);
    }

    /**
     * 响应json数据
     * @author: ZBoHang
     * @time: 2021/10/12 9:56
     */
    public static void responseMsg(HttpServletResponse response, String result) {
        // 设置编码
        response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_CONTENT_TYPE);
        // 写数据
        write(response, result, CommonConstants.APPLICATION_JSON_CONTENT_TYPE);
    }
}
