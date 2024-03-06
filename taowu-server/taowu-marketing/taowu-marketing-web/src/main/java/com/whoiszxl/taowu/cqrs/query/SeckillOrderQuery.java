package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀订单列表查询对象
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "秒杀订单列表查询对象")
public class SeckillOrderQuery extends PageQuery {

    @Query
    @Schema(description = "关联秒杀表的主键ID")
    private Long seckillId;

    @Query
    @Schema(description = "sku名称")
    private String skuName;
}
