package com.whoiszxl.taowu.common.exception.custom;

import lombok.NoArgsConstructor;

/**
 * 业务异常
 * @author whoiszxl
 */
@NoArgsConstructor
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
