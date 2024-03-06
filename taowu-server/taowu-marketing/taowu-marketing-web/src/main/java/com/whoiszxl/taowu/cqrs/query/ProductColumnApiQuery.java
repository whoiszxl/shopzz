package com.whoiszxl.taowu.cqrs.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品专栏表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@Schema(description = "商品专栏表")
public class ProductColumnApiQuery {

    @NotNull(message = "专栏ID不允许为空")
    @Schema(description = "专栏ID")
    private Long id;

}
