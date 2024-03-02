package com.whoiszxl.cqrs.query;

import com.whoiszxl.core.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "订单列表查询参数")
public class OrderListQuery extends PageQuery {

    @Schema(description = "订单状态：1:待付款,2:已取消,3:待发货,4:待收货,5:已完成")
    private Integer orderStatus;
}
