package com.whoiszxl.service.impl;

import com.whoiszxl.entity.OrderOperateHistory;
import com.whoiszxl.mapper.OrderOperateHistoryMapper;
import com.whoiszxl.service.OrderOperateHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-30
 */
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

}
