package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.entity.Counter;
import com.whoiszxl.taowu.mapper.CounterMapper;
import com.whoiszxl.taowu.service.ICounterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 计数表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-19
 */
@Service
public class CounterServiceImpl extends ServiceImpl<CounterMapper, Counter> implements ICounterService {

}
