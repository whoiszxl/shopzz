package com.whoiszxl.controller.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Banner;
import com.whoiszxl.entity.Category;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.vo.CategoryVo;
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
	
	@GetMapping("get_category")
    @ApiOperation(value = "获取分类接口")
    public ServerResponse<List<CategoryVo>> getChildrenParallelCategory(@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
       //查询子节点的category信息,并且不递归,保持平级
       return categoryService.getChildrenParallelCategory(categoryId);
    }

	@GetMapping("get_deep_category")
    @ApiOperation(value = "获取当前分类和子集分类的分类id接口")
    public ServerResponse<?> getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        return categoryService.selectCategoryAndChildrenById(categoryId);

    }
}
