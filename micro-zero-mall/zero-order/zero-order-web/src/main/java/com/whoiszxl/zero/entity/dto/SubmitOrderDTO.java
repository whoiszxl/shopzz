package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.entity.Order;
import com.whoiszxl.zero.entity.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/27
 */
@Data
public class SubmitOrderDTO {

    private Order order;

    private List<OrderItem> orderItemList;

    private BigDecimal payPrice;

    private BigDecimal fare;
}
