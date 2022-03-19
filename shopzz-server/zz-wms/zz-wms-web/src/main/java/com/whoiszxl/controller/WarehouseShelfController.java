package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.WarehouseShelfSaveCommand;
import com.whoiszxl.cqrs.command.WarehouseShelfUpdateCommand;
import com.whoiszxl.cqrs.query.WarehouseShelfQuery;
import com.whoiszxl.cqrs.response.WarehouseShelfResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.WarehouseShelf;
import com.whoiszxl.service.WarehouseShelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 仓库货架相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/shelf")
@Api(tags = "仓库货架相关接口")
public class WarehouseShelfController {

    @Autowired
    private WarehouseShelfService warehouseShelfService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取仓库货架列表", notes = "分页获取仓库货架列表", response = WarehouseShelfResponse.class)
    public ResponseResult<IPage<WarehouseShelfResponse>> list(@RequestBody WarehouseShelfQuery query) {
        LambdaQueryWrapper<WarehouseShelf> wrapper = new LambdaQueryWrapper<>();
        if(query.getWarehouseId() != null) {
            wrapper.eq(WarehouseShelf::getWarehouseId, query.getWarehouseId());
        }

        IPage<WarehouseShelf> result = warehouseShelfService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<WarehouseShelfResponse> finalResult = result.convert(e -> dozerUtils.map(e, WarehouseShelfResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取仓库货架", notes = "通过主键ID获取仓库货架", response = WarehouseShelfResponse.class)
    public ResponseResult<WarehouseShelfResponse> getSupplierById(@PathVariable Long id) {
        WarehouseShelf warehouse = warehouseShelfService.getById(id);
        return warehouse == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouse, WarehouseShelfResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增仓库货架", notes = "新增仓库货架", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody WarehouseShelfSaveCommand warehouseShelfSaveCommand) {
        WarehouseShelf warehouse = dozerUtils.map(warehouseShelfSaveCommand, WarehouseShelf.class);
        boolean saveFlag = warehouseShelfService.save(warehouse);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新仓库货架", notes = "更新仓库货架", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody WarehouseShelfUpdateCommand warehouseShelfUpdateCommand) {
        WarehouseShelf warehouse = dozerUtils.map(warehouseShelfUpdateCommand, WarehouseShelf.class);
        boolean updateFlag = warehouseShelfService.updateById(warehouse);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除仓库货架", notes = "删除仓库货架", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = warehouseShelfService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}
