package com.whoiszxl.taowu.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.ReflectUtils;
import com.whoiszxl.taowu.cqrs.command.CategorySaveCommand;
import com.whoiszxl.taowu.cqrs.command.CategoryUpdateCommand;
import com.whoiszxl.taowu.cqrs.query.CategoryQuery;
import com.whoiszxl.taowu.entity.Category;
import com.whoiszxl.taowu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 商品三级分类表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/category")
@Tag(name = "分类后台相关接口")
@RequiredArgsConstructor
public class CategoryAdminController extends BaseController<CategoryService, Category, Category, CategoryQuery, CategorySaveCommand, CategoryUpdateCommand> {

    private final CategoryService categoryService;

    @Operation(summary = "获取分类列表", description = "获取分类列表")
    @SaCheckPermission(value = {"product:category:list"})
    @ResponseBody
    @GetMapping("/tree/list")
    public ResponseResult<List<Tree<Long>>> categoryTreeList(@Validated CategoryQuery categoryQuery) {
        List<Category> menuResponseList = this.responseList(categoryQuery);
        TreeNodeConfig config = new TreeNodeConfig();
        List<Tree<Long>> result = TreeUtil.build(menuResponseList, 0L, config, (((object, treeNode) -> {
            List<Field> fieldList = ReflectUtils.getNonStaticFields(Category.class);
            fieldList.forEach(f -> treeNode.putExtra(f.getName(), ReflectUtil.invoke(object, StrUtil.genGetter(f.getName()))));
        })));

        return ResponseResult.buildSuccess(result);
    }

    @Operation(summary = "以树形结构获取菜单列表", description = "以树形结构获取菜单列表")
    @SaCheckPermission(value = {"system:menu:list"})
    @ResponseBody
    @GetMapping("/common/tree")
    protected ResponseResult<List<Tree<Long>>> menuTree(@Validated CategoryQuery categoryQuery) {
        List<Category> menuResponseList = this.responseList(categoryQuery);
        TreeNodeConfig config = new TreeNodeConfig();
        config.setNameKey("title");
        config.setIdKey("key");
        config.setWeightKey("sort");

        List<Tree<Long>> result = TreeUtil.build(menuResponseList, 0L, config, (((object, treeNode) -> {
            treeNode.setId(object.getId());
            treeNode.setParentId(object.getParentId());
            treeNode.setName(object.getName());
            treeNode.setWeight(object.getSort());
        })));

        return ResponseResult.buildSuccess(result);
    }



    @SaCheckPermission("product:category:add")
    @SaCheckLogin
    @PostMapping("/add")
    @Operation(summary = "新增分类", description = "新增分类")
    public ResponseResult<Boolean> add(@RequestBody @Validated CategorySaveCommand categorySaveCommand) {
        boolean saveFlag = categoryService.save(categorySaveCommand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckPermission("product:category:update")
    @SaCheckLogin
    @PutMapping("/update")
    @Operation(summary = "更新分类", description = "更新分类")
    public ResponseResult<Boolean> update(@RequestBody CategoryUpdateCommand categoryUpdateCommand) {
        boolean updateFlag = categoryService.update(categoryUpdateCommand);
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckPermission("product:category:delete")
    @SaCheckLogin
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除无下级的分类", description = "删除无下级的分类")
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = categoryService.removeNoChildCategoryById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

