package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件删除命令
 *
 * @author whoiszxl
 * @date 2022/3/23
 */
@ApiModel("文件删除命令")
public class FileDeleteCommand {

    @ApiModelProperty("文件ID")
    private Long id;

    @ApiModelProperty("最终文件名")
    private String finalFileName;

    @ApiModelProperty("相对路径")
    private String relativePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinalFileName() {
        return finalFileName;
    }

    public void setFinalFileName(String finalFileName) {
        this.finalFileName = finalFileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
