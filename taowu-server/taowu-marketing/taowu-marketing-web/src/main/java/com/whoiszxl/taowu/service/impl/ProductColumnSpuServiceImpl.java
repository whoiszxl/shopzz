package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.entity.ProductColumnSpu;
import com.whoiszxl.taowu.mapper.ProductColumnSpuMapper;
import com.whoiszxl.taowu.service.ProductColumnSpuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品专栏跟SPU关联表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
@RequiredArgsConstructor
public class ProductColumnSpuServiceImpl extends ServiceImpl<ProductColumnSpuMapper, ProductColumnSpu> implements ProductColumnSpuService {

}
