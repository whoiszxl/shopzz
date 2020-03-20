package com.whoiszxl.common.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@ApiModel(value = "RestErrorResponse", description = "错误响应参数包装")
@Data
public class RestErrorResponse {

    private String errCode;

    private String errMessage;

    public RestErrorResponse(String errCode,String errMessage){
        this.errCode = errCode;
        this.errMessage= errMessage;
    }


}
