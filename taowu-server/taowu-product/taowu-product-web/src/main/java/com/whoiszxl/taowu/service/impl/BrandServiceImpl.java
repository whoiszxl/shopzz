package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.Brand;
import com.whoiszxl.taowu.mapper.BrandMapper;
import com.whoiszxl.taowu.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
