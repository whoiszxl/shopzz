package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "Sku查询对象")
public class SkuQuery extends PageQuery {

    @Query
    @Schema(description = "SPU ID")
    private Long spuId;

    @Query(blurry = "sku_name")
    @Schema(description = "SKU名称")
    private String skuName;


}
