package com.whoiszxl.controller;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Brand;
import com.whoiszxl.entity.dto.CategoryDTO;
import com.whoiszxl.entity.vo.CategoryListTreeVO;
import com.whoiszxl.entity.vo.CategoryVO;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品三级分类表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Slf4j
@RestController
@RequestMapping("/category")
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list/tree")
    @ApiOperation(value = "以树状形式返回所有分类", notes = "以树状形式返回所有分类", response = CategoryListTreeVO.class)
    public ResponseResult<CategoryListTreeVO> listTree() {
        List<CategoryDTO> categoryDTOS = categoryService.listWithTree();
        List<CategoryVO> categoryVOS = BeanCopierUtils.copyListProperties(categoryDTOS, CategoryVO::new);
        return ResponseResult.buildSuccess(new CategoryListTreeVO(categoryVOS));
    }
}


