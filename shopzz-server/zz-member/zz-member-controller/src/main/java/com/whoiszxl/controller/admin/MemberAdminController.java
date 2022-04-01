package com.whoiszxl.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.command.MemberApplicationService;
import com.whoiszxl.command.cmd.MemberLoginCommand;
import com.whoiszxl.command.cmd.UpdateMemberCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.query.MemberQueryApplicationService;
import com.whoiszxl.query.model.qry.MemberQuery;
import com.whoiszxl.query.model.response.MemberDetailResponse;
import com.whoiszxl.query.model.response.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理后台相关接口
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@RestController
@RequestMapping("/member")
@Api(tags = "用户管理后台相关接口")
public class MemberAdminController {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberQueryApplicationService memberQueryApplicationService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取用户列表", notes = "分页获取用户列表", response = MemberResponse.class)
    public ResponseResult<IPage<MemberResponse>> list(@RequestBody MemberQuery query) {

        IPage<MemberResponse> result = memberQueryApplicationService.list(query);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @DeleteMapping("/ban/{memberId}")
    @ApiOperation(value = "禁用用户", notes = "禁用用户", response = ResponseResult.class)
    public ResponseResult<Boolean> ban(@PathVariable Long memberId) {
        memberApplicationService.ban(memberId);
        return ResponseResult.buildSuccess();
    }


    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取用户", notes = "通过主键ID获取用户", response = WarehouseResponse.class)
    public ResponseResult<WarehouseResponse> getSupplierById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getById(id);
        return warehouse == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouse, WarehouseResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增用户", notes = "新增用户", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody WarehouseSaveCommand warehouseSaveCommand) {
        Warehouse warehouse = dozerUtils.map(warehouseSaveCommand, Warehouse.class);
        boolean saveFlag = warehouseService.save(warehouse);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新用户", notes = "更新用户", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody WarehouseUpdateCommand warehouseSaveCommand) {
        Warehouse warehouse = dozerUtils.map(warehouseSaveCommand, Warehouse.class);
        boolean updateFlag = warehouseService.updateById(warehouse);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "删除用户", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = warehouseService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}
