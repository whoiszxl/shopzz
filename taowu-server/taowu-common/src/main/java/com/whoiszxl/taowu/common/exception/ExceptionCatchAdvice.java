package com.whoiszxl.taowu.common.exception;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.exception.custom.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author whoiszxl
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatchAdvice {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> catchException(Exception e, HttpServletRequest request) {
        log.error("ExceptionCatchAdvice|发生未知异常|{}", request.getRequestURL(), e);
        return ResponseResult.buildError(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> catchHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return ResponseResult.buildError(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ResponseResult<String> catchServiceException(ServiceException e, HttpServletRequest request) {
        return ResponseResult.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<String> catchMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return ResponseResult.buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
