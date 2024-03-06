package com.whoiszxl.taowu.cqrs.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品专栏跟SPU关联表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@Schema(description = "商品专栏跟SPU关联表")
public class ProductColumnSpuResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "专栏主键ID")
    private Long columnId;

    @Schema(description = "SPU主键ID")
    private Long spuId;


}
