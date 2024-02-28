package com.whoiszxl.taowu.file.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "上传返回参数")
public class UploadResponse implements Serializable {

    @Schema(description = "上传图片的url地址")
    private String url;

    @Schema(description = "上传图片的新名称")
    private String filename;

    @Schema(description = "原始文件名")
    private String originalFilename;

}
