package com.whoiszxl.taowu.common.exception;

import com.whoiszxl.taowu.common.exception.custom.ServiceException;

/**
 * 异常捕获工具
 * @author whoiszxl
 */
public class ExceptionCatcher {

    public static void catchServiceEx(String message) {
        throw new ServiceException(message);
    }
}
