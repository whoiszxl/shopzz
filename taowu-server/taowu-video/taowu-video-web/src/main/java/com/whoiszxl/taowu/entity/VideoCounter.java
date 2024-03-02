package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 视频计数表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Data
@TableName("vms_video_counter")
@Schema(description = "视频计数表")
public class VideoCounter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "视频ID，也是主键ID")
    private Long videoId;

    @Schema(description = "计数类型： 1:观看量 2:转发量 3:评论量 4:点赞量")
    private Integer counterType;

    @Schema(description = "计数值")
    private Long counterValue;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "业务状态")
    private Integer status;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
