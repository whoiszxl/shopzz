package com.whoiszxl.taowu.admin.controller.sys;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.whoiszxl.taowu.admin.cqrs.command.MenuAddCommand;
import com.whoiszxl.taowu.admin.cqrs.query.MenuQuery;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.utils.ReflectUtils;
import com.whoiszxl.taowu.admin.cqrs.command.MenuUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.response.MenuResponse;
import com.whoiszxl.taowu.admin.entity.Menu;
import com.whoiszxl.taowu.admin.service.IMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 系统菜单 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Tag(name = "menu菜单 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController<IMenuService, Menu, MenuResponse, MenuQuery, MenuAddCommand, MenuUpdateCommand> {


    @Operation(summary = "获取菜单列表", description = "获取菜单列表")
    @SaCheckPermission(value = {"system:menu:list"})
    @ResponseBody
    @GetMapping("/tree/list")
    public ResponseResult<List<Tree<Integer>>> menuList(@Validated MenuQuery menuQuery) {
        List<MenuResponse> menuResponseList = this.responseList(menuQuery);
        TreeNodeConfig config = new TreeNodeConfig();
        List<Tree<Integer>> result = TreeUtil.build(menuResponseList, 0, config, (((object, treeNode) -> {
            List<Field> fieldList = ReflectUtils.getNonStaticFields(MenuResponse.class);
            fieldList.forEach(f -> treeNode.putExtra(f.getName(), ReflectUtil.invoke(object, StrUtil.genGetter(f.getName()))));
        })));

        return ResponseResult.buildSuccess(result);
    }


    @Operation(summary = "以树形结构获取菜单列表", description = "以树形结构获取菜单列表")
    @SaCheckPermission(value = {"system:menu:list"})
    @ResponseBody
    @GetMapping("/common/tree")
    protected ResponseResult<List<Tree<Integer>>> menuTree(@Validated MenuQuery menuQuery) {
        List<MenuResponse> menuResponseList = this.responseList(menuQuery);
        TreeNodeConfig config = new TreeNodeConfig();
        config.setNameKey("title");
        config.setIdKey("key");
        config.setWeightKey("sort");

        List<Tree<Integer>> result = TreeUtil.build(menuResponseList, 0, config, (((object, treeNode) -> {
            treeNode.setId(object.getId());
            treeNode.setParentId(object.getParentId());
            treeNode.setName(object.getName());
            treeNode.setWeight(object.getSort());
        })));

        return ResponseResult.buildSuccess(result);
    }


    @PostMapping("/add")
    @SaCheckPermission(value = {"system:menu:add"})
    @Operation(summary = "管理后台新增菜单", description = "管理后台新增菜单")
    public ResponseResult<String> addMenu(@Validated @RequestBody MenuAddCommand command) {
        baseService.addMenu(command);
        return ResponseResult.buildSuccess();
    }

    @Operation(summary = "管理后台更新菜单", description = "管理后台更新菜单")
    @ResponseBody
    @SaCheckPermission(value = {"system:menu:update"})
    @PutMapping("/update")
    protected ResponseResult<String> updateMenu(@RequestBody MenuUpdateCommand menuUpdateCommand) {
        baseService.updateMenu(menuUpdateCommand);
        return ResponseResult.buildSuccess();
    }
    
    @Operation(summary = "批量删除菜单", description = "传入菜单ID集合，批量删除菜单")
    @SaCheckPermission(value = {"system:menu:delete"})
    @ResponseBody
    @DeleteMapping("/delete/{ids}")
    protected ResponseResult menuDelete(@PathVariable List<Integer> ids) {
        baseService.deleteMenu(ids);
        return ResponseResult.buildSuccess();
    }

}

