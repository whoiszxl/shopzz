package com.whoiszxl.product.handler;

import com.whoiszxl.common.entity.BusinessException;
import com.whoiszxl.common.entity.CommonErrorCode;
import com.whoiszxl.common.entity.ErrorCode;
import com.whoiszxl.common.entity.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 全局异常处理器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Slf4j
@ControllerAdvice//与@Exceptionhandler配合使用实现全局异常处理
public class GlobalExceptionHandler {

    //捕获Exception异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processExcetion(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Exception e) {
        //解析异常信息
        //如果是系统自定义异常，直接取出errCode和errMessage
        if (e instanceof BusinessException) {
            log.info(e.getMessage(), e);
            //解析系统自定义异常信息
            BusinessException businessException = (BusinessException) e;
            ErrorCode errorCode = businessException.getErrorCode();
            //错误代码
            int code = errorCode.getCode();
            //错误信息
            String desc = errorCode.getDesc();
            return new RestErrorResponse(String.valueOf(code), desc);
        }

        log.error("系统异常：", e);
        //统一定义为99999系统未知错误
        return new RestErrorResponse(String.valueOf(CommonErrorCode.UNKNOWN.getCode()), CommonErrorCode.UNKNOWN.getDesc());

    }
}
