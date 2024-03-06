package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.entity.OrderInvoice;
import com.whoiszxl.taowu.mapper.OrderInvoiceMapper;
import com.whoiszxl.taowu.service.OrderInvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单发票表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Service
@RequiredArgsConstructor
public class OrderInvoiceServiceImpl extends ServiceImpl<OrderInvoiceMapper, OrderInvoice> implements OrderInvoiceService {

}
