package com.whoiszxl.taowu.state;

import com.whoiszxl.taowu.constants.OrderStatusConstants;
import com.whoiszxl.taowu.entity.Order;
import com.whoiszxl.taowu.mapper.OrderMapper;
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
