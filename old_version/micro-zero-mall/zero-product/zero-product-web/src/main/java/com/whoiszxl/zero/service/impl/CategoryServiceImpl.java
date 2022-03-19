package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.dao.CategoryDao;
import com.whoiszxl.zero.entity.Category;
import com.whoiszxl.zero.entity.dto.CategoryDTO;
import com.whoiszxl.zero.service.CategoryService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDTO> listWithTree() {
        List<Category> categories = categoryDao.findAll();
        List<CategoryDTO> categoryDTOS = BeanCopierUtils.copyListProperties(categories, CategoryDTO::new);

        List<CategoryDTO> collect = categoryDTOS.stream().filter(item -> item.getParentId() == 0)
                .peek(menu -> menu.setChildren(getChildrens(menu, categoryDTOS)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return collect;
    }

    private List<CategoryDTO> getChildrens(CategoryDTO categoryDTO, List<CategoryDTO> categoryDTOS) {
        List<CategoryDTO> collect = categoryDTOS.stream().filter(item -> item.getParentId().equals(categoryDTO.getId()))
                .peek(menu -> menu.setChildren(getChildrens(menu, categoryDTOS)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return collect;
    }
}
