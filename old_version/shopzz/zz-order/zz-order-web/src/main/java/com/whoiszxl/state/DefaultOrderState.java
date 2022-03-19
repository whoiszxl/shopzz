package com.whoiszxl.state;

import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.entity.Order;
import com.whoiszxl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认的订单状态组件
 * @author whoiszxl
 *
 */
@Component
public class DefaultOrderState extends AbstractOrderState {

	@Autowired
	public DefaultOrderState(OrderService orderService) {
		super(orderService);
	}
	
	@Override
	protected Integer getOrderStatus(Order order) {
		return OrderStatusConstants.UNKNOWN;
	}
	
}
