package com.whoiszxl.taowu.file.cqrs.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文件删除命令
 *
 * @author whoiszxl
 * @date 2022/3/23
 */
@Data
@Schema(description = "文件删除命令")
public class FileDeleteCommand {

    @Schema(description = "文件ID")
    private Long id;

    @Schema(description = "最终文件名")
    private String finalFileName;

    @Schema(description = "相对路径")
    private String relativePath;

}
