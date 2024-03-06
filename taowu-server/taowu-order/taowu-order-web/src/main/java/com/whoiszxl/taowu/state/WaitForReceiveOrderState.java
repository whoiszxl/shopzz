package com.whoiszxl.taowu.state;

import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.mapper.OrderMapper;
import com.whoiszxl.taowu.constants.OrderStatusConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 待收货状态
 * @author whoiszxl
 *
 */
@Component
public class WaitForReceiveOrderState extends AbstractOrderState {

	@Autowired
	public WaitForReceiveOrderState(OrderMapper orderMapper) {
		super(orderMapper);
	}

	@Override
	protected Integer getOrderStatus(Order order) {
		return OrderStatusConstants.WAIT_FOR_RECEIVE;
	}
	
	@Override
	public Boolean canConfirmReceipt(Order order) {
		return true;
	}

}
