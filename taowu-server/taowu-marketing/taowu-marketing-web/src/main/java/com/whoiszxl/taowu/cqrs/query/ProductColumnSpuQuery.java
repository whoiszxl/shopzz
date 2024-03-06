package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品专栏表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "商品专栏表")
public class ProductColumnSpuQuery extends PageQuery {

    @Query
    @Schema(description = "专栏主键ID")
    private Long columnId;

}
