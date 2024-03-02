package com.whoiszxl.taowu.common.exception.custom;

import lombok.NoArgsConstructor;

/**
 * 业务异常
 * @author whoiszxl
 */
@NoArgsConstructor
public class AssertException extends RuntimeException {

    public AssertException(String message) {
        super(message);
    }
}
