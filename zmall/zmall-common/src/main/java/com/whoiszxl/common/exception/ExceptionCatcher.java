package com.whoiszxl.common.exception;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.exception.custom.JwtAuthException;
import com.whoiszxl.common.exception.custom.ValidateException;

/**
 * @description: 异常捕获
 * @author: whoiszxl
 * @create: 2020-01-07
 **/
public class ExceptionCatcher {

    public static void catchJwtAuthEx(){
        throw new JwtAuthException();
    }

    public static void catchValidateEx(Result result){
        throw new ValidateException(result);
    }

}
