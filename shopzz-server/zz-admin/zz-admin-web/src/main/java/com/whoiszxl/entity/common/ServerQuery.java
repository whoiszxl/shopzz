package com.whoiszxl.entity.common;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务器查询参数
 *
 * @author whoiszxl
 * @date 2021/8/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="服务器查询参数", description="服务器查询参数")
public class ServerQuery extends PageQuery {

    @ApiModelProperty(value = "服务器名称")
    private String serverName;
}
