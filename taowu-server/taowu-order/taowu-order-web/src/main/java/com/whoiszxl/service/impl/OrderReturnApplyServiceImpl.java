package com.whoiszxl.service.impl;

import com.whoiszxl.entity.OrderReturnApply;
import com.whoiszxl.mapper.OrderReturnApplyMapper;
import com.whoiszxl.service.OrderReturnApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单退货表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
@RequiredArgsConstructor
public class OrderReturnApplyServiceImpl extends ServiceImpl<OrderReturnApplyMapper, OrderReturnApply> implements OrderReturnApplyService {

}
