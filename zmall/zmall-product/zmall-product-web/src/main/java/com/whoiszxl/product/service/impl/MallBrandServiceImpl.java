package com.whoiszxl.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.product.mapper.MallBrandMapper;
import com.whoiszxl.product.entity.MallBrand;
import com.whoiszxl.product.service.MallBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
@Slf4j
@Service
@Transactional
public class MallBrandServiceImpl extends ServiceImpl<MallBrandMapper, MallBrand> implements MallBrandService {

    @Autowired
    private MallBrandMapper mallBrandMapper;

    @Override
    public List<Map> findBrandListByCategoryName(String categoryName) {
        return mallBrandMapper.findBrandListByCategoryName(categoryName);
    }
}
