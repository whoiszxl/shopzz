package com.whoiszxl.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;

/**
 * 异常处理
 * @author whoiszxl
 *
 */
@RestControllerAdvice
public class JwtExceptionController {

	
	private static Logger log = LoggerFactory.getLogger("全局异常");
	
	/**
	 * 捕捉shiro的异常
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ShiroException.class)
	public ServerResponse<String> handle401(ShiroException e) {
		return ServerResponse.createByErrorCodeMessage(ResponseCode.LOGIN_AUTH_FAIL.getCode(), "token不存在");
	}
	
	// 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ServerResponse<String> handle401() {
        return ServerResponse.createByErrorCodeMessage(ResponseCode.LOGIN_AUTH_FAIL.getCode(), "token无效");
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServerResponse<String> globalException(HttpServletRequest request, Throwable ex) {
    	log.error("错误地址：" + request.getRequestURI());
		log.error("错误信息：" + ex.getMessage());
        return ServerResponse.createByErrorCodeMessage(getStatus(request).value(), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
