package com.whoiszxl.taowu.file.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-23
 */
@Data
@TableName("fms_file")
@Schema(description = "文件表")
public class FmsFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文件主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "文件所属用户ID")
    @TableField(value = "`member_id`")
    private Long memberId;

    @Schema(description = "文件访问地址")
    @TableField(value = "url")
    private String url;

    @Schema(description = "文件大小，单位字节")
    @TableField(value = "`size`")
    private Long size;


    @Schema(description = "文件名称")
    @TableField(value = "filename")
    private String filename;


    @Schema(description = "原始文件名")
    @TableField(value = "original_filename")
    private String originalFilename;


    @Schema(description = "基础存储路径")
    @TableField(value = "base_path")
    private String basePath;


    @Schema(description = "存储路径")
    @TableField(value = "`path`")
    private String path;


    @Schema(description = "文件扩展名")
    @TableField(value = "ext")
    private String ext;


    @Schema(description = "MIME类型")
    @TableField(value = "content_type")
    private String contentType;


    @Schema(description = "存储平台")
    @TableField(value = "platform")
    private String platform;


    @Schema(description = "缩略图访问路径")
    @TableField(value = "th_url")
    private String thUrl;


    @Schema(description = "缩略图名称")
    @TableField(value = "th_filename")
    private String thFilename;


    @Schema(description = "缩略图大小，单位字节")
    @TableField(value = "th_size")
    private Long thSize;


    @Schema(description = "缩略图MIME类型")
    @TableField(value = "th_content_type")
    private String thContentType;


    @Schema(description = "文件所属对象id")
    @TableField(value = "object_id")
    private String objectId;


    @Schema(description = "文件所属对象类型，例如用户头像，评价图片")
    @TableField(value = "object_type")
    private String objectType;


    @Schema(description = "文件元数据")
    @TableField(value = "metadata")
    private String metadata;


    @Schema(description = "文件用户元数据")
    @TableField(value = "user_metadata")
    private String userMetadata;


    @Schema(description = "缩略图元数据")
    @TableField(value = "th_metadata")
    private String thMetadata;


    @Schema(description = "缩略图用户元数据")
    @TableField(value = "th_user_metadata")
    private String thUserMetadata;


    @Schema(description = "附加属性")
    @TableField(value = "attr")
    private String attr;


    @Schema(description = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    public static final String COL_ID = "id";

    public static final String COL_URL = "url";

    public static final String COL_SIZE = "size";

    public static final String COL_FILENAME = "filename";

    public static final String COL_ORIGINAL_FILENAME = "original_filename";

    public static final String COL_BASE_PATH = "base_path";

    public static final String COL_PATH = "path";

    public static final String COL_EXT = "ext";

    public static final String COL_CONTENT_TYPE = "content_type";

    public static final String COL_PLATFORM = "platform";

    public static final String COL_TH_URL = "th_url";

    public static final String COL_TH_FILENAME = "th_filename";

    public static final String COL_TH_SIZE = "th_size";

    public static final String COL_TH_CONTENT_TYPE = "th_content_type";

    public static final String COL_OBJECT_ID = "object_id";

    public static final String COL_OBJECT_TYPE = "object_type";

    public static final String COL_METADATA = "metadata";

    public static final String COL_USER_METADATA = "user_metadata";

    public static final String COL_TH_METADATA = "th_metadata";

    public static final String COL_TH_USER_METADATA = "th_user_metadata";

    public static final String COL_ATTR = "attr";

    public static final String COL_CREATE_TIME = "create_time";
}
