package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.mapper.SeckillItemMapper;
import com.whoiszxl.taowu.service.SeckillItemService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SeckillItemServiceImpl extends ServiceImpl<SeckillItemMapper, SeckillItem> implements SeckillItemService {

    private final SeckillItemMapper seckillItemMapper;

    @Override
    public boolean subDbStock(Long seckillItemId, Integer quantity) {
        return seckillItemMapper.subDbStock(seckillItemId, quantity);
    }

    @Override
    public boolean addDbStock(Long seckillItemId, Integer quantity) {
        return seckillItemMapper.addDbStock(seckillItemId, quantity);
    }
}
