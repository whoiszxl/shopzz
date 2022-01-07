package com.whoiszxl.utils;

import com.whoiszxl.exception.custom.AssertException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 断言
 */
public class AssertUtils {


    /**
     * 断言为真
     *
     * @param flag
     * @param message
     */
    public static void isTrue(boolean flag, String message) {
        if (!flag) {
            throw new AssertException(message);
        }
    }

    /**
     * 断言为假
     *
     * @param flag
     * @param message
     */
    public static void isFalse(boolean flag, String message) {
        if (flag) {
            throw new AssertException(message);
        }
    }

    /**
     * 断言对象不为空
     *
     * @param obj
     * @param message
     */
    public static void isNotNull(Object obj, String message) {
        if (ObjectUtils.isEmpty(obj)) {
            throw new AssertException(message);
        }
    }

    /**
     * 断言对象为空
     *
     * @param obj
     * @param message
     */
    public static void isNull(Object obj, String message) {
        if (!ObjectUtils.isEmpty(obj)) {
            throw new AssertException(message);
        }
    }

    /**
     * 断言字符串必须有值
     *
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException(message);
        }
    }

    /**
     * 断言字符串必须是可转Double类型
     * @param text
     * @param message
     */
    public static void isDouble(String text, String message) {
        try {
            Double.parseDouble(text);
        }catch (NumberFormatException e) {
            throw new AssertException(message);
        }
    }

    private AssertUtils() {
    }
}
