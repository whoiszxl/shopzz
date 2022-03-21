package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Category;
import com.whoiszxl.mapper.CategoryMapper;
import com.whoiszxl.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品三级分类表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
