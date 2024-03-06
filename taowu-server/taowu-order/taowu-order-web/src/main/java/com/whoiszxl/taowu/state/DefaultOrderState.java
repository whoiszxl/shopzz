package com.whoiszxl.taowu.state;

import com.whoiszxl.taowu.constants.OrderStatusConstants;
import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.mapper.OrderMapper;
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
	public DefaultOrderState(OrderMapper orderMapper) {
		super(orderMapper);
	}
	
	@Override
	protected Integer getOrderStatus(Order order) {
		return OrderStatusConstants.UNKNOWN;
	}
	
}
