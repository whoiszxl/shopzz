package com.whoiszxl.taowu.common.utils;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 日志格式化工具
 */
@NoArgsConstructor
public class LogFormatUtil {

    /**
     * 日志格式化样式
     */
    private static final String FORMAT = "%s|%s|%s";

    /**
     * 日志格式化
     * @param o 类名，模块名
     * @param desc 日志描述
     * @param params 日志参数
     * @return 格式化后的日志
     */
    public static String format(Object o, String desc, Object... params) {
        String paramsStr = StringUtils.join(params, ",");
        return String.format(FORMAT, o.getClass().getName(), desc, paramsStr);
    }



}
