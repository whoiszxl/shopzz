package com.whoiszxl.service;

import com.whoiszxl.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.dto.CategoryDTO;

import java.util.List;

/**
 * <p>
 * 商品三级分类表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
public interface CategoryService extends IService<Category> {

    /**
     * 树状查找分类列表
     * @return 分类列表
     */
    List<CategoryDTO> listWithTree();
}
