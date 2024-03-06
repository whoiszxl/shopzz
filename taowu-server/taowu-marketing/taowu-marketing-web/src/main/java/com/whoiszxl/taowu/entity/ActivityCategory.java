package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 促销活动跟分类的关联关系表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@TableName("spms_activity_category")
@Schema(description = "促销活动跟分类的关联关系表")
public class ActivityCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "促销活动ID")
    private Long activityId;

    @Schema(description = "分类ID")
    private Long categoryId;


}
