package com.whoiszxl.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mall_order_log")
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 操作员
     */
    private String operater;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 付款状态
     */
    private String payStatus;

    /**
     * 发货状态
     */
    private String consignStatus;

    /**
     * 备注
     */
    private String remarks;


}
