package com.whoiszxl.entity.schedule;

import com.whoiszxl.bean.AbstractObject;
import com.whoiszxl.dto.OrderItemDTO;
import com.whoiszxl.entity.SaleDeliveryOrderPickingItem;
import lombok.Data;

import java.util.List;

/**
 * 调度销售出库的结果
 */
@Data
public class SaleDeliveryScheduleResult extends AbstractObject {

	/**
	 * 订单条目
	 */
	private OrderItemDTO orderItem;
	/**
	 * 拣货条目
	 */
	private List<SaleDeliveryOrderPickingItem> pickingItems;

}
