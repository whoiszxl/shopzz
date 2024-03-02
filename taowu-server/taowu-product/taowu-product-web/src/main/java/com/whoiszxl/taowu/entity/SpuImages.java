package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品SPU图片表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_spu_images")
@Schema(description = "商品SPU图片表")
public class SpuImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "商品ID")
    private Long spuId;

    @Schema(description = "图片地址")
    private String imgUrl;

    @Schema(description = "排序,降序排列")
    private Integer sort;

    @Schema(description = "是否默认图")
    private Integer isDefault;


}
