package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.entity.Category;
import com.whoiszxl.zero.entity.dto.CategoryDTO;
import com.whoiszxl.zero.entity.vo.CategoryVO;
import com.whoiszxl.zero.service.CategoryService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "分类控制器")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "以树状形式返回所有分类")
    @GetMapping("/list/tree")
    public List<CategoryVO> listTree() {
        List<CategoryDTO> categoryDTOS = categoryService.listWithTree();
        return BeanCopierUtils.copyListProperties(categoryDTOS, CategoryVO::new);
    }
}
