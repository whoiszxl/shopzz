package com.whoiszxl.zero.exception.custom;

/**
 * 断言异常
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class AssertException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AssertException() {
        super();
    }
    public AssertException(String message) {
        super(message);
    }
}