package com.whoiszxl.taowu.admin.controller.sys;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.whoiszxl.taowu.admin.cqrs.command.RoleAddCommand;
import com.whoiszxl.taowu.admin.cqrs.query.RoleQuery;
import com.whoiszxl.taowu.admin.cqrs.command.RoleUpdateCommand;
import com.whoiszxl.taowu.admin.cqrs.response.RoleDetailResponse;
import com.whoiszxl.taowu.admin.cqrs.response.RoleResponse;
import com.whoiszxl.taowu.admin.entity.Role;
import com.whoiszxl.taowu.admin.service.IRoleService;
import com.whoiszxl.taowu.common.base.BaseController;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.response.DictResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-03-02
 */
@Tag(name = "role角色 API")
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController<IRoleService, Role, RoleResponse, RoleQuery, RoleAddCommand, RoleUpdateCommand> {
    
    @Operation(summary = "查询角色字典列表", description = "查询角色字典列表")
    @GetMapping("/dict")
    public ResponseResult<List<DictResponse<Integer>>> listRoleDict(@Validated RoleQuery query) {
        List<RoleResponse> roleResponseList = this.responseList(query);
        List<DictResponse<Integer>> result = roleResponseList
                .stream()
                .map(role -> new DictResponse<>(role.getName(), role.getId()))
                .collect(Collectors.toList());
        return ResponseResult.buildSuccess(result);
    }

    @PostMapping("/add")
    @SaCheckPermission(value = {"system:role:add"})
    @Operation(summary = "管理后台新增角色", description = "管理后台新增角色")
    public ResponseResult<String> addRole(@Validated @RequestBody RoleAddCommand command) {
        baseService.addRole(command);
        return ResponseResult.buildSuccess();
    }

    @Operation(summary = "管理后台更新角色", description = "管理后台更新角色")
    @ResponseBody
    @SaCheckPermission(value = {"system:role:update"})
    @PutMapping("/update")
    protected ResponseResult<String> updateRole(@RequestBody RoleUpdateCommand roleUpdateCommand) {
        baseService.updateRole(roleUpdateCommand);
        return ResponseResult.buildSuccess();
    }

    @GetMapping("/detail/{roleId}")
    @SaCheckPermission(value = {"system:role:list"})
    @Operation(summary = "获取角色的详情信息", description = "获取角色的详情信息,并带上角色相关信息")
    public ResponseResult<RoleDetailResponse> roleDetail(@PathVariable Integer roleId) {
        RoleDetailResponse detailResponse = baseService.roleDetail(roleId);
        return ResponseResult.buildSuccess(detailResponse);
    }

    @Operation(summary = "批量删除角色", description = "传入角色ID集合，批量删除角色")
    @SaCheckPermission(value = {"system:role:delete"})
    @ResponseBody
    @DeleteMapping("/delete/{ids}")
    protected ResponseResult roleDelete(@PathVariable List<Integer> ids) {
        baseService.roleDelete(ids);
        return ResponseResult.buildSuccess();
    }

    @Operation(summary = "切换角色的状态", description = "切换角色状态")
    @PatchMapping("/switch/status/{roleId}")
    @SaCheckPermission(value = {"system:role:switch-status"})
    public ResponseResult<String> switchStatus(@PathVariable Integer roleId) {
        baseService.switchStatus(roleId);
        return ResponseResult.buildSuccess();
    }
}

