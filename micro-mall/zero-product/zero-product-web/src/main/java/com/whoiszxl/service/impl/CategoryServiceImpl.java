package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Category;
import com.whoiszxl.entity.dto.CategoryDTO;
import com.whoiszxl.mapper.CategoryMapper;
import com.whoiszxl.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> listWithTree() {
        //1. 将所有的分类加载到内存中
        List<Category> categories = categoryMapper.selectList(null);
        List<CategoryDTO> categoryDTOS = BeanCopierUtils.copyListProperties(categories, CategoryDTO::new);

        //2. 过滤出一级分类，然后再去查找一级分类的子类，直到查询到最后一级，通过sort字段进行排序
        List<CategoryDTO> collect = categoryDTOS.stream().filter(item -> item.getParentId() == 0)
                .peek(menu -> menu.setChildren(getChildrens(menu, categoryDTOS)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return collect;
    }

    /**
     * 在所有分类中查询到categoryDTO的所有子分类
     * @param categoryDTO 需要查询子分类的父类
     * @param categoryDTOS 所有分类
     * @return
     */
    private List<CategoryDTO> getChildrens(CategoryDTO categoryDTO, List<CategoryDTO> categoryDTOS) {
        List<CategoryDTO> collect = categoryDTOS.stream().filter(item -> item.getParentId().equals(categoryDTO.getId()))
                .peek(menu -> menu.setChildren(getChildrens(menu, categoryDTOS)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return collect;
    }
}
