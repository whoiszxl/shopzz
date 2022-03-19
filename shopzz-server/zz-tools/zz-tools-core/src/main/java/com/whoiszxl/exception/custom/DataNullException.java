package com.whoiszxl.exception.custom;


import com.whoiszxl.bean.ResponseResult;

/**
 * 校验异常
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
public class DataNullException extends RuntimeException {

    //错误代码
    private final ResponseResult result;

    public DataNullException(ResponseResult result){
        this.result = result;
    }
    public ResponseResult getResult(){
        return result;
    }
}