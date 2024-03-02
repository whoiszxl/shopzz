package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品SPU表
 * </p>
 *
 * @author gupaoedu
 * @since 2023-01-06
 */
@Data
@Schema(description = "首页SPU返回实体")
public class IndexSpuResponse implements Serializable {


    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "默认价格")
    private BigDecimal defaultPrice;

    @Schema(description = "商品默认图片地址")
    private String defaultPic;

}
