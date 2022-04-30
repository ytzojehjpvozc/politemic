package com.xbh.politemic.common.util;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.common.enums.ResultEnum;
import com.xbh.politemic.common.exception.ServiceException;
import org.springframework.lang.Nullable;

/**
 * @ServiceAssert: srv 断言
 * @author: ZBoHang
 * @time: 2021/12/9 17:08
 */
public class ServiceAssert {

    private ServiceAssert() {}

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new ServiceException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new ServiceException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ServiceException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void hasBlank(@Nullable String text, String message) {
        if (!StrUtil.hasBlank(text)) {
            throw new ServiceException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void noneBlank(@Nullable String text, String message) {
        if (StrUtil.hasBlank(text)) {
            throw new ServiceException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }
}
