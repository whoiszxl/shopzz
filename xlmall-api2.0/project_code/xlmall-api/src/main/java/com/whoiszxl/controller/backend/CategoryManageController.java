package com.whoiszxl.controller.backend;

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
import com.whoiszxl.service.CategoryService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value="后台分类管理模块",description="后台分类管理模块")
@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {
	
	@Autowired
    private CategoryService categoryService;
	
	@PostMapping("/add_category")
	@ApiOperation(value = "添加分类接口")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(value="parentId",defaultValue="0") int parentId) {
		return categoryService.addCategory(categoryName, parentId);
	}
	
	@PostMapping("set_category_name")
	@ApiOperation(value = "更新分类接口")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
    public ServerResponse<String> setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        //更新categoryName
        return categoryService.updateCategoryName(categoryId,categoryName); 
    }

    @GetMapping("get_category")
    @ApiOperation(value = "获取分类接口")
    @RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
    public ServerResponse<?> getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
       //查询子节点的category信息,并且不递归,保持平级
       return categoryService.getChildrenParallelCategory(categoryId);
    }

    @GetMapping("get_deep_category")
    @ApiOperation(value = "获取当前分类和子集分类的分类id接口")
    @RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
    public ServerResponse<?> getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        return categoryService.selectCategoryAndChildrenById(categoryId);

    }
	
}
