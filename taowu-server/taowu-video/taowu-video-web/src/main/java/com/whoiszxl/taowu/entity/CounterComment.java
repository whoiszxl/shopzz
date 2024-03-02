package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 视频计数表
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-29
 */
@Data
@TableName("base_counter_comment")
@Schema(description = "视频计数表")
public class CounterComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    @Schema(description = "获赞数")
    private Integer likeCount;


}
