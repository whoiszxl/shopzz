package com.whoiszxl.entity.order;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("oms_order_operate_history")
@ApiModel(value = "OrderOperateHistory对象", description = "订单操作历史记录表")
public class OrderOperateHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("操作类型，1：创建订单，2：手动取消订单，3：自动取消订单，4：支付订单，5：手动确认收货，6：自动确认收货，7：商品发货，8：申请退货，9：退货审核不通过，10：退货审核通过，11：寄送退货商品，12：确认收到退货，13：退货已入库，14：完成退款")
    private Integer operateType;

    @ApiModelProperty("操作备注")
    private String operateNote;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
