package com.whoiszxl.entity.vo;

import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("订单创建信息对象")
public class OrderCreateInfo {

    private Order order;

    private List<OrderItem> orderItemList;

    private BigDecimal payPrice;

    private BigDecimal fare;
}
