package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.WarehouseSaveCommand;
import com.whoiszxl.cqrs.command.WarehouseUpdateCommand;
import com.whoiszxl.cqrs.query.WarehouseQuery;
import com.whoiszxl.cqrs.response.WarehouseResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Warehouse;
import com.whoiszxl.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 仓库相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@RestController
@RequestMapping("/warehouse")
@Api(tags = "仓库相关接口")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取仓库列表", notes = "分页获取仓库列表", response = WarehouseResponse.class)
    public ResponseResult<IPage<WarehouseResponse>> list(@RequestBody WarehouseQuery query) {
        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getWarehouseName())) {
            wrapper.like(Warehouse::getWarehouseName, "%" + query.getWarehouseName() + "%");
        }
        if(StringUtils.isNotBlank(query.getWarehouseAdminName())) {
            wrapper.eq(Warehouse::getWarehouseAdminName, query.getWarehouseAdminName());
        }
        IPage<Warehouse> result = warehouseService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<WarehouseResponse> finalResult = result.convert(e -> dozerUtils.map(e, WarehouseResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取仓库", notes = "通过主键ID获取仓库", response = WarehouseResponse.class)
    public ResponseResult<WarehouseResponse> getSupplierById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getById(id);
        return warehouse == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouse, WarehouseResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增仓库", notes = "新增仓库", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody WarehouseSaveCommand warehouseSaveCommand) {
        Warehouse warehouse = dozerUtils.map(warehouseSaveCommand, Warehouse.class);
        boolean saveFlag = warehouseService.save(warehouse);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新仓库", notes = "更新仓库", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody WarehouseUpdateCommand warehouseSaveCommand) {
        Warehouse warehouse = dozerUtils.map(warehouseSaveCommand, Warehouse.class);
        boolean updateFlag = warehouseService.updateById(warehouse);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除仓库", notes = "删除仓库", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = warehouseService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}
