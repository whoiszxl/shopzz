package com.whoiszxl.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 管理员搜索Vo
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Data
@ApiModel(value="BrandSearchVo", description="管理员搜索Vo")
public class AdminSearchVo implements Serializable {


    @ApiModelProperty(value = "页码")
    private Long id;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "页码")
    private Long page;

    @ApiModelProperty(value = "页数")
    private Long size;

}
