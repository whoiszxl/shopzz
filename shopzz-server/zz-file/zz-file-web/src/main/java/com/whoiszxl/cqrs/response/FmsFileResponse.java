package com.whoiszxl.cqrs.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@ApiModel(value = "File对象", description = "文件表")
public class FmsFileResponse implements Serializable {

    @ApiModelProperty("品牌主键id")
    private Long id;

    @ApiModelProperty("平台类型: 1-阿里云 2-七牛云 3-百度云 4-AmazonS3")
    private Integer platformType;

    @ApiModelProperty("业务ID")
    private String bizId;

    @ApiModelProperty("业务类型: 1-商品 2-会员 3-wms")
    private Integer bizType;

    @ApiModelProperty("数据类型: 1-目录 2-图片 3-视频 4-音频 5-文档 6-其他")
    private Integer dataType;

    @ApiModelProperty("文件内容类型")
    private String contentType;

    @ApiModelProperty("原始文件名")
    private String originalFileName;

    @ApiModelProperty("最终文件名")
    private String finalFileName;

    @ApiModelProperty("相对路径")
    private String relativePath;

    @ApiModelProperty("访问地址")
    private String url;

    @ApiModelProperty("md5值")
    private String md5;

    @ApiModelProperty("文件后缀")
    private String ext;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("创建年份: yyyy")
    private String createdYear;

    @ApiModelProperty("创建年月: yyyy-MM")
    private String createdMonth;

    @ApiModelProperty("创建年月日: yyyy-MM-dd")
    private String createdDay;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
