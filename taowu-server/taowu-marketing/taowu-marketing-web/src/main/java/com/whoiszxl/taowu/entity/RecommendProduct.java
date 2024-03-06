package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 首页通用推荐商品表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-06
 */
@Data
@TableName("spms_recommend_product")
@Schema(description = "首页通用推荐商品表")
public class RecommendProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "SPU主键ID")
    private Long spuId;


}
