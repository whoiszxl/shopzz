package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.param.SubmitOrderParam;
import com.whoiszxl.zero.entity.vo.OrderConfirmVO;
import com.whoiszxl.zero.entity.vo.OrderVO;

/**
 * TODO
 *
 * @author zhouxiaolong
 * @date 2021/4/27
 */
public interface OrderService {

    /**
     * 提交订单
     * @param param
     * @return
     */
    OrderVO submitOrder(SubmitOrderParam param);

    /**
     * 确认订单信息
     * @return
     */
    OrderConfirmVO confirmOrder();
}
