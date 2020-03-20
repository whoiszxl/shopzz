package com.whoiszxl.common.entity;

/**
 * @description: 自定义的异常类型
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
    public BusinessException() {
        super();
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

