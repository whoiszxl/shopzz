package com.whoiszxl.state;

import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.entity.Order;
import com.whoiszxl.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 已完成状态
 * @author whoiszxl
 *
 */
@Component
public class FinishedOrderState extends AbstractOrderState {

	@Autowired
	public FinishedOrderState(OrderMapper orderMapper) {
		super(orderMapper);
	}

	@Override
	protected Integer getOrderStatus(Order order) {
		return OrderStatusConstants.FINISHED;
	}


	@Override
	public Boolean canApplyReturnProduct(Order order) {
		return true;
	}

}
