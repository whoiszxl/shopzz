package com.whoiszxl.exception;

/**
 * jwt未授权异常
 * @author whoiszxl
 *
 */
public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
