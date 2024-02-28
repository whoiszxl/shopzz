package com.whoiszxl.taowu.file.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@Data
@Schema(description = "文件表")
public class FmsFileResponse implements Serializable {

    @Schema(description = "品牌主键id")
    private Long id;

    @Schema(description = "平台类型: 1-阿里云 2-七牛云 3-百度云 4-AmazonS3")
    private Integer platformType;

    @Schema(description = "业务ID")
    private String bizId;

    @Schema(description = "业务类型: 1-商品 2-会员 3-wms")
    private Integer bizType;

    @Schema(description = "数据类型: 1-目录 2-图片 3-视频 4-音频 5-文档 6-其他")
    private Integer dataType;

    @Schema(description = "文件内容类型")
    private String contentType;

    @Schema(description = "原始文件名")
    private String originalFileName;

    @Schema(description = "最终文件名")
    private String finalFileName;

    @Schema(description = "相对路径")
    private String relativePath;

    @Schema(description = "访问地址")
    private String url;

    @Schema(description = "md5值")
    private String md5;

    @Schema(description = "文件后缀")
    private String ext;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "创建年份: yyyy")
    private String createdYear;

    @Schema(description = "创建年月: yyyy-MM")
    private String createdMonth;

    @Schema(description = "创建年月日: yyyy-MM-dd")
    private String createdDay;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
