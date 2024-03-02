package com.whoiszxl.state;

import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.entity.Order;
import com.whoiszxl.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 待发货状态
 * @author whoiszxl
 *
 */
@Component
public class WaitForDeliveryOrderState extends AbstractOrderState {

	@Autowired
	public WaitForDeliveryOrderState(OrderMapper orderMapper) {
		super(orderMapper);
	}

	@Override
	protected Integer getOrderStatus(Order order) {
		return OrderStatusConstants.WAIT_FOR_DELIVERY;
	}

}
