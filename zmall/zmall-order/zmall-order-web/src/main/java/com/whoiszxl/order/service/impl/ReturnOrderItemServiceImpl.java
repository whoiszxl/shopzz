package com.whoiszxl.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.order.entity.ReturnOrderItem;
import com.whoiszxl.order.mapper.ReturnOrderItemMapper;
import com.whoiszxl.order.service.ReturnOrderItemService;
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
public class ReturnOrderItemServiceImpl extends ServiceImpl<ReturnOrderItemMapper, ReturnOrderItem> implements ReturnOrderItemService {

}
