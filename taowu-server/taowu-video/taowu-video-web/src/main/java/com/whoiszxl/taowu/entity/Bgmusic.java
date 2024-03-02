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
 * 背景音乐表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Data
@TableName("vms_bgmusic")
@Schema(description = "背景音乐表")
public class Bgmusic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "背景音乐主键ID")
    private Long id;

    @Schema(description = "音乐名")
    private String musicName;

    @Schema(description = "歌手")
    private String singer;

    @Schema(description = "音乐封面")
    private String cover;

    @Schema(description = "作曲者")
    private String musicComposer;

    @Schema(description = "作词者")
    private String musicLyricist;

    @Schema(description = "音乐版权")
    private String musicCopyright;

    @Schema(description = "音乐秒数")
    private Float musicSeconds;

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
