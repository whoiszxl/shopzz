package com.whoiszxl.entity.vo;

import com.whoiszxl.bean.AbstractObject;
import com.whoiszxl.entity.Order;
import com.whoiszxl.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("订单创建信息对象")
public class OrderCreateInfo extends AbstractObject implements Serializable {

    private Order order;

    private List<OrderItem> orderItemList;

    private BigDecimal payPrice;

    private BigDecimal fare;
}
