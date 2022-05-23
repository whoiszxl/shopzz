package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.mapper.SeckillItemMapper;
import com.whoiszxl.service.SeckillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀item表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Service
public class SeckillItemServiceImpl extends ServiceImpl<SeckillItemMapper, SeckillItem> implements SeckillItemService {

    @Autowired
    private SeckillItemMapper seckillItemMapper;

    @Override
    public boolean subDbStock(Long seckillItemId, Integer quantity) {
        return seckillItemMapper.subDbStock(seckillItemId, quantity);
    }

    @Override
    public boolean addDbStock(Long seckillItemId, Integer quantity) {
        return seckillItemMapper.addDbStock(seckillItemId, quantity);
    }
}
