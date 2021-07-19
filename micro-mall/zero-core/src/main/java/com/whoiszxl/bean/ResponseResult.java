package com.whoiszxl.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description: 返回实体类
 * @author: whoiszxl
 * @create: 2020-01-03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResponseResult<T> {

    /** 返回码 */
    private Integer code;
    /** 返回信息 */
    private String message;
    /** 返回数据 */
    private T data;

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isOk() {
        return this.code.equals(StatusCode.OK);
    }

    public static <T> ResponseResult<T> buildError() {
        ResponseResult<T> result = new ResponseResult<T>();
        return result.setCode(StatusCode.ERROR).setMessage("error");
    }

    public static <T> ResponseResult<T> buildError(String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        return result.setCode(StatusCode.ERROR).setMessage(message);
    }

    public static <T> ResponseResult<T> buildError(int errorCode, String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        return result.setCode(errorCode).setMessage(message);
    }

    public static <T> ResponseResult<T> buildSuccess(T data) {
        return buildSuccess("success", data);
    }

    public static <T> ResponseResult<T> buildSuccess(String message, T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(StatusCode.OK).setMessage(message).setData(data);
        return result;
    }

    public static <T> ResponseResult<T> buildSuccess() {
        ResponseResult<T> result = new ResponseResult<T>();
        return result.setCode(StatusCode.OK).setMessage("success");
    }

    public static <T> ResponseResult<T> buildByFlag(boolean flag) {
        return flag ? ResponseResult.buildSuccess() : ResponseResult.buildError();
    }
}
