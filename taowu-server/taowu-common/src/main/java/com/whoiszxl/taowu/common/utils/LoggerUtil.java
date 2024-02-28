package com.whoiszxl.taowu.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 * 日志输出工具
 */
public class LoggerUtil {

    /**
     * 日志格式化样式
     */
    private static final String FORMAT = "%s|%s|%s";

    /**
     * 日志格式化
     * @param module 模块名
     * @param desc 日志描述
     * @param params 日志参数
     * @return 格式化后的日志
     */
    private static String format(String module, String desc, Object... params) {
        if(params != null && params.length > 0) {
            String paramsStr = StringUtils.join(params, ",");
            return String.format(FORMAT, module, desc, paramsStr);
        }
        return null;
    }


    public static void info(Logger logger, String module, String desc, Object... params) {
        if(logger.isInfoEnabled()) {
            logger.info(format(module, desc, params));
        }
    }

    public static void warn(Logger logger, String module, String desc, Object... params) {
        if(logger.isWarnEnabled()) {
            logger.warn(format(module, desc, params));
        }
    }

    public static void debug(Logger logger, String module, String desc, Object... params) {
        if(logger.isDebugEnabled()) {
            logger.debug(format(module, desc, params));
        }
    }

    public static void error(Logger logger, String module, String desc, Object... params) {
        if(logger.isErrorEnabled()) {
            logger.error(format(module, desc, params));
        }
    }

}
