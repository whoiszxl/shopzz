package com.whoiszxl.service.impl;

import com.whoiszxl.cqrs.command.SkuSaveCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.mapper.SkuMapper;
import com.whoiszxl.service.SkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public void save(SkuSaveCommand skuSaveCommand) {
        Sku sku = dozerUtils.map(skuSaveCommand, Sku.class);

    }

}
