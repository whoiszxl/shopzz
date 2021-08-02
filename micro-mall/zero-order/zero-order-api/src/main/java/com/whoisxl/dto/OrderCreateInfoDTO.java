package com.whoisxl.dto;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("订单创建信息对象")
public class OrderCreateInfoDTO extends AbstractObject implements Serializable {

    private OrderDTO order;

    private List<OrderItemDTO> orderItemList;

    private BigDecimal payPrice;

    private BigDecimal fare;
}
