package com.whoiszxl.exception.custom;


import com.whoiszxl.bean.ResponseResult;

/**
 * 数据库更新错误
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class DatabaseUpdateException extends RuntimeException {

    //错误代码
    private final ResponseResult result;

    public DatabaseUpdateException(ResponseResult result){
        this.result = result;
    }
    public ResponseResult getResult(){
        return result;
    }
}