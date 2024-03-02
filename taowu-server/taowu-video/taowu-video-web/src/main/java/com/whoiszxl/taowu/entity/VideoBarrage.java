package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 视频弹幕表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Data
@TableName("vms_video_barrage")
@Schema(description = "视频弹幕表")
public class VideoBarrage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "弹幕内容")
    private String content;

    @Schema(description = "弹幕发送时间")
    private LocalDateTime sendAt;


}
