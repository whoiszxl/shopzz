package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.dto.PurchaseSupplierDTO;
import com.whoiszxl.entity.PurchaseSupplier;
import com.whoiszxl.entity.query.SupplierQuery;
import com.whoiszxl.entity.vo.PurchaseSupplierVO;
import com.whoiszxl.enums.CloneDirectionEnum;
import com.whoiszxl.service.PurchaseSupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 采购供应商表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/wms/purchase-supplier")
@Api(tags = "采购供应商相关接口")
public class PurchaseSupplierController {

    @Autowired
    private PurchaseSupplierService purchaseSupplierService;

    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "分页获取供应商列表", notes = "分页获取供应商列表", response = PurchaseSupplierVO.class)
    public ResponseResult<IPage<PurchaseSupplier>> list(SupplierQuery query) {
        QueryWrapper<PurchaseSupplier> wrapper = new QueryWrapper<>();
        if(query.getSupplierName() != null) {
            wrapper.like("supplier_name", "%" + query.getSupplierName() + "%");
        }
        if(query.getAccountPeriod() != null) {
            wrapper.eq("account_period", query.getAccountPeriod());
        }
        IPage<PurchaseSupplier> result = purchaseSupplierService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "提交采购单去审核", notes = "提交采购单去审核", response = PurchaseSupplierVO.class)
    public ResponseResult<PurchaseSupplierVO> getSupplierById(@PathVariable Long id) {
        PurchaseSupplier purchaseSupplier = purchaseSupplierService.getById(id);
        return purchaseSupplier == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(purchaseSupplier.clone(PurchaseSupplierVO.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增供应商", notes = "新增供应商", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody PurchaseSupplierVO purchaseSupplierVO) throws Exception {
        PurchaseSupplier purchaseSupplier = purchaseSupplierVO.clone(PurchaseSupplier.class);
        boolean saveFlag = purchaseSupplierService.save(purchaseSupplier);
        return ResponseResult.buildByFlag(saveFlag);
    }

}

