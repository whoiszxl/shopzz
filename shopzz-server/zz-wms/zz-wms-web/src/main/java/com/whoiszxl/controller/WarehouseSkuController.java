package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.WarehouseSkuQuery;
import com.whoiszxl.cqrs.response.WarehouseResponse;
import com.whoiszxl.cqrs.response.WarehouseSkuResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.WarehouseSku;
import com.whoiszxl.service.WarehouseSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 仓库SKU相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/sku")
@Api(tags = "仓库SKU相关接口")
public class WarehouseSkuController {

    @Autowired
    private WarehouseSkuService warehouseSkuService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取仓库SKU列表", notes = "分页获取仓库SKU列表", response = WarehouseSkuResponse.class)
    public ResponseResult<IPage<WarehouseSkuResponse>> list(@RequestBody WarehouseSkuQuery query) {
        LambdaQueryWrapper<WarehouseSku> wrapper = new LambdaQueryWrapper<>();
        if(query.getShelfId() != null) {
            wrapper.like(WarehouseSku::getShelfId, query.getShelfId());
        }
        if(StringUtils.isNotBlank(query.getSkuName())) {
            wrapper.like(WarehouseSku::getSkuName, "%" + query.getSkuName() + "%");
        }
        if(StringUtils.isNotBlank(query.getSkuCode())) {
            wrapper.like(WarehouseSku::getSkuCode, "%" + query.getSkuCode() + "%");
        }

        IPage<WarehouseSku> result = warehouseSkuService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<WarehouseSkuResponse> finalResult = result.convert(e -> dozerUtils.map(e, WarehouseSkuResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取仓库SKU", notes = "通过主键ID获取仓库SKU", response = WarehouseResponse.class)
    public ResponseResult<WarehouseSkuResponse> getSupplierById(@PathVariable Long id) {
        WarehouseSku warehouseSku = warehouseSkuService.getById(id);
        return warehouseSku == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(warehouseSku, WarehouseSkuResponse.class));
    }

}
