package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品基础信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@Schema(description = "商品基础信息表")
public class ColumnSpuApiResponse implements Serializable {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "商品副名称")
    private String subName;

    @Schema(description = "默认价格")
    private BigDecimal defaultPrice;

    @Schema(description = "商品默认图片地址")
    private String defaultPic;

}
