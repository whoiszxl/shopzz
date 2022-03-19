package com.whoiszxl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.PurchaseSupplierSaveCommand;
import com.whoiszxl.cqrs.query.SupplierQuery;
import com.whoiszxl.cqrs.response.PurchaseSupplierResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.PurchaseSupplier;
import com.whoiszxl.service.PurchaseSupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 采购供应商相关接口
 *
 * @author whoiszxl
 * @date 2022/3/18
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/purchase/supplier")
@Api(tags = "采购供应商相关接口")
public class PurchaseSupplierController {

    @Autowired
    private PurchaseSupplierService purchaseSupplierService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "分页获取供应商列表", notes = "分页获取供应商列表", response = PurchaseSupplierResponse.class)
    public ResponseResult<IPage<PurchaseSupplierResponse>> list(SupplierQuery query) {
        LambdaQueryWrapper<PurchaseSupplier> wrapper = new LambdaQueryWrapper<>();
        if(query.getSupplierName() != null) {
            wrapper.like(PurchaseSupplier::getSupplierName, "%" + query.getSupplierName() + "%");
        }
        if(query.getAccountPeriod() != null) {
            wrapper.eq(PurchaseSupplier::getAccountPeriod, query.getAccountPeriod());
        }
        IPage<PurchaseSupplier> result = purchaseSupplierService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        IPage<PurchaseSupplierResponse> finalResult = result.convert(e -> dozerUtils.map(e, PurchaseSupplierResponse.class));
        return ResponseResult.buildSuccess(finalResult);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取供应商", notes = "通过主键ID获取供应商", response = PurchaseSupplierResponse.class)
    public ResponseResult<PurchaseSupplierResponse> getSupplierById(@PathVariable Long id) {
        PurchaseSupplier purchaseSupplier = purchaseSupplierService.getById(id);
        return purchaseSupplier == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(dozerUtils.map(purchaseSupplier, PurchaseSupplierResponse.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增供应商", notes = "新增供应商", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody PurchaseSupplierSaveCommand purchaseSupplierSaveCommand) {
        PurchaseSupplier purchaseSupplier = dozerUtils.map(purchaseSupplierSaveCommand, PurchaseSupplier.class);
        boolean saveFlag = purchaseSupplierService.save(purchaseSupplier);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新供应商", notes = "更新供应商", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody PurchaseSupplierSaveCommand purchaseSupplierSaveCommand) {
        PurchaseSupplier purchaseSupplier = dozerUtils.map(purchaseSupplierSaveCommand, PurchaseSupplier.class);
        boolean updateFlag = purchaseSupplierService.updateById(purchaseSupplier);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除供应商", notes = "删除供应商", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = purchaseSupplierService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }
}
