package com.whoiszxl.cqrs.dto;

import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单创建信息对象DTO
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@Data
@ApiModel("订单创建信息对象")
public class OrderCreateInfoDTO implements Serializable {

    private Order order;

    private List<OrderItem> orderItemList;

    private BigDecimal payPrice;

    private BigDecimal fare;
}
