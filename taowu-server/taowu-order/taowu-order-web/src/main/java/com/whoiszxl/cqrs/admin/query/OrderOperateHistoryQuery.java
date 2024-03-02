package com.whoiszxl.cqrs.admin.query;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.whoiszxl.core.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单操作历史记录表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@Schema(description = "订单操作历史记录查询条件")
public class OrderOperateHistoryQuery implements Serializable {

    @Query
    @Schema(description = "主键")
    private Long id;

    @Query
    @Schema(description = "订单ID")
    private Long orderId;

    @Query
    @Schema(description = "操作类型，1：创建订单，2：手动取消订单，3：自动取消订单，4：支付订单，5：手动确认收货，6：自动确认收货，7：商品发货，8：申请退货，9：退货审核不通过，10：退货审核通过，11：寄送退货商品，12：确认收到退货，13：退货已入库，14：完成退款")
    private Integer operateType;

}
