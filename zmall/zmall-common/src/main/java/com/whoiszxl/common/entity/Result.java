package com.whoiszxl.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Result<T>", description = "响应通用参数包装")
public class Result<T> {

    @ApiModelProperty("响应错误编码,0为正常")
    private int code;

    @ApiModelProperty("响应错误信息")
    private String msg;

    @ApiModelProperty("响应内容")
    private T result;

    public static <T> Result<T> success() {
        return new Result<T>();
    }

    public static <T> Result<T> success(T result) {
        Result<T> response = new Result<T>();
        response.setResult(result);
        return response;
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> response = new Result<T>();
        response.setCode(-2);
        response.setMsg(msg);
        return response;
    }

    public Result() {
        this(0, "");
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
