package com.xbh.politemic.common.util;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.common.enums.ResultEnum;
import com.xbh.politemic.common.exception.ApiException;
import org.springframework.lang.Nullable;

/**
 * @ApiAssert: api 断言
 * @author: ZBoHang
 * @time: 2021/12/9 17:07
 */
public class ApiAssert {

    private ApiAssert() {}

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ApiException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new ApiException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ApiException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void hasBlank(@Nullable String text, String message) {
        if (!StrUtil.hasBlank(text)) {
            throw new ApiException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }

    public static void noneBlank(@Nullable String text, String message) {
        if (StrUtil.hasBlank(text)) {
            throw new ApiException(ResultEnum.PARAMS_NONE_PASS.getCode(), message);
        }
    }
}
