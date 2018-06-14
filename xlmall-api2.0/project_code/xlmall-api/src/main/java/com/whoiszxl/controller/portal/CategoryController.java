package com.whoiszxl.controller.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.vo.ProductDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl 前台分类模块
 */
@Api(value = "前台分类模块", description = "前台分类模块")
@RestController
@RequestMapping("/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@ApiOperation(value = "前台：获取主页分类推荐栏目，每个大类取前五个", notes = "前台：获取主页分类推荐栏目，每个大类取前五个")
	@GetMapping("categorys")
	public ServerResponse<List<HashMap<String, Object>>> categoryList() {
		return categoryService.getIndexPageCategorys(Const.Article.CATEGORY_MAIN_COUNT);
	}
}
