package com.whoiszxl.state;

import com.whoiszxl.constants.OrderStatusConstants;
import com.whoiszxl.entity.Order;
import com.whoiszxl.mapper.OrderMapper;
import com.whoiszxl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 等待退货申请审核状态
 * @author whoiszxl
 *
 */
@Component
public class WaitForReturnProductApproveOrderState extends AbstractOrderState {

	@Autowired
	public WaitForReturnProductApproveOrderState(OrderMapper orderMapper) {
		super(orderMapper);
	}

	@Override
	protected Integer getOrderStatus(Order order) {
		return OrderStatusConstants.WAIT_FOR_RETURN_PRODUCT_APPROVE;
	}

}
