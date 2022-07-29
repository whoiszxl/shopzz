package com.whoiszxl.entity.common;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查看文件查询参数
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="查看文件查询参数", description="查看文件查询参数")
public class ViewFileQuery extends PageQuery {

    @ApiModelProperty(value = "文件的绝对地址")
    private String absolutePath;

    @ApiModelProperty(value = "服务器主键ID")
    private Integer serverId;
}
