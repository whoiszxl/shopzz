package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/3/23
 */
@Data
@ApiModel("文件删除命令")
public class FileDeleteCommand {

    @ApiModelProperty("文件ID")
    private Long id;

    @ApiModelProperty("最终文件名")
    private String finalFileName;

    @ApiModelProperty("相对路径")
    private String relativePath;

}
