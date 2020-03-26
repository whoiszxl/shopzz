package com.whoiszxl.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.order.entity.OrderConfig;
import com.whoiszxl.order.mapper.OrderConfigMapper;
import com.whoiszxl.order.service.OrderConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-26
 */
@Slf4j
@Service
@Transactional
public class OrderConfigServiceImpl extends ServiceImpl<OrderConfigMapper, OrderConfig> implements OrderConfigService {

}
