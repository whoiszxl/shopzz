package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.entity.OrderOperateHistory;
import com.whoiszxl.taowu.mapper.OrderOperateHistoryMapper;
import com.whoiszxl.taowu.service.OrderOperateHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
@RequiredArgsConstructor
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

}
