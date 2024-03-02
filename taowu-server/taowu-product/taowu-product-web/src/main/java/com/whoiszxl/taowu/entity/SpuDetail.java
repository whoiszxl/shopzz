package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品详情页数据表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@TableName("pms_spu_detail")
@Schema(description = "商品详情页数据表")
public class SpuDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private Long spuId;

    @Schema(description = "PC商品详情富文本内容")
    private String detailHtml;

    @Schema(description = "移动端商品详情富文本内容")
    private String detailMobile;


}
