package com.whoiszxl.swagger.bean;

import lombok.Data;

/**
 * 全局参数配置
 *
 * @author whoiszxl
 * @date 2021/11/30
 */
@Data
public class GlobalOperationParameter {
    /**
     * 参数名
     **/
    private String name;

    /**
     * 描述信息
     **/
    private String description = "全局参数";

    /**
     * 指定参数类型
     **/
    private String modelRef = "String";

    /**
     * 参数放在哪个地方:header,query,path,body.form
     **/
    private String parameterType = "header";

    /**
     * 参数是否必须传
     **/
    private Boolean required = false;
    /**
     * 默认值
     */
    private String defaultValue = "";
    /**
     * 允许为空
     */
    private Boolean allowEmptyValue = true;
    /**
     * 排序
     */
    private int order = 1;
}