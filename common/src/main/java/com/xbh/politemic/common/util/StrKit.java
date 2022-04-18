package com.xbh.politemic.common.util;

import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import com.xbh.politemic.common.constant.Constants;
import org.springframework.util.DigestUtils;

import java.util.Locale;
import java.util.UUID;

/**
 * @Description: 自定义字符串工具类
 * @Author: zhengbohang
 * @Date: 2021/10/3 18:43
 */
public class StrKit {

    private static String commonGetUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @description: 获取32位随机字符串
     * @author: zhengbohang
     * @date: 2021/10/3 21:52
     */
    public static String getUUID() {
        return commonGetUUID().replaceAll("-", "");
    }
    /**
     * @description: 获取随机盐值
     * @author: zhengbohang
     * @date: 2021/10/4 10:40
     */
    public static String getSalt(int startIndex, int endIndex) {
        return getUUID().substring(startIndex, endIndex);
    }
    /**
     * @description: 获取一个随机的验证码
     * @author: zhengbohang
     * @date: 2021/10/4 12:20
     */
    public static String getValidateCode() {
        return getSalt(Constants.VALIDATE_CODE_START_INDEX, Constants.VALIDATE_CODE_END_INDEX).toUpperCase(Locale.ROOT);
    }

    /**
     * @description: md5加密
     * @author: zhengbohang
     * @date: 2021/10/3 22:03
     */
    public static String MD5Code(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * @description: 验证邮箱
     * @author: zhengbohang
     * @date: 2021/10/4 10:18
     */
    public static boolean validEmail(String str) {
        return ReUtil.isMatch(RegexPool.EMAIL, str);
    }

}
