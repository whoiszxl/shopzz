package com.whoiszxl.exception;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.exception.custom.DataNullException;
import com.whoiszxl.exception.custom.DatabaseUpdateException;
import com.whoiszxl.exception.custom.JwtAuthException;
import com.whoiszxl.exception.custom.ValidateException;

/**
 * 异常捕获
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class ExceptionCatcher {

    public static void catchAuthFailEx(){
        throw new JwtAuthException();
    }

    public static void catchDatabaseFailEx(){
        throw new DatabaseUpdateException(ResponseResult.buildError("数据库更新错误"));
    }

    public static void catchValidateEx(ResponseResult result){
        throw new ValidateException(result);
    }

    public static void catchNullEx(ResponseResult result) {
        throw new DataNullException(result);
    }

}