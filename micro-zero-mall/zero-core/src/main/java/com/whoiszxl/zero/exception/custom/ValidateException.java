package com.whoiszxl.zero.exception.custom;

import com.whoiszxl.zero.bean.Result;

/**
 * 校验异常
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class ValidateException extends RuntimeException {

    //错误代码
    private final Result result;

    public ValidateException(Result result){
        this.result = result;
    }
    public Result getResult(){
        return result;
    }
}