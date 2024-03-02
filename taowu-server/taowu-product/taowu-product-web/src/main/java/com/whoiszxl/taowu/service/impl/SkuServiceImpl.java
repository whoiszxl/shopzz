package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.Sku;
import com.whoiszxl.taowu.mapper.SkuMapper;
import com.whoiszxl.taowu.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku信息表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
@RequiredArgsConstructor
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

}
