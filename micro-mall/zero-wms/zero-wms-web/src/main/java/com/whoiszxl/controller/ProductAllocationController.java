package com.whoiszxl.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.ProductAllocation;
import com.whoiszxl.entity.query.ProductAllocationQuery;
import com.whoiszxl.entity.vo.ProductAllocationVO;
import com.whoiszxl.service.ProductAllocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 货位表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-21
 */
@Slf4j
@RestController
@RequestMapping("/product-allocation")
@Api(tags = "货位相关接口")
public class ProductAllocationController {


    @Autowired
    private ProductAllocationService productAllocationService;

    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "分页获取货位列表", notes = "分页获取货位列表", response = ProductAllocation.class)
    public ResponseResult<IPage<ProductAllocation>> list(ProductAllocationQuery query) {
        QueryWrapper<ProductAllocation> wrapper = new QueryWrapper<>();
        if(query.getProductAllocationCode() != null) {
            wrapper.like("supplier_name", "%" + query.getProductAllocationCode() + "%");
        }
        IPage<ProductAllocation> result = productAllocationService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取货位", notes = "通过主键ID获取货位", response = ProductAllocationVO.class)
    public ResponseResult<ProductAllocationVO> getSupplierById(@PathVariable Long id) {
        ProductAllocation productAllocation = productAllocationService.getById(id);
        return productAllocation == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(productAllocation.clone(ProductAllocationVO.class));
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增货位", notes = "新增货位", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody ProductAllocationVO productAllocationVO) {
        ProductAllocation productAllocation = productAllocationVO.clone(ProductAllocation.class);
        boolean saveFlag = productAllocationService.save(productAllocation);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新货位", notes = "更新货位", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody ProductAllocationVO productAllocationVO) {
        ProductAllocation productAllocation = productAllocationVO.clone(ProductAllocation.class);
        boolean updateFlag = productAllocationService.updateById(productAllocation);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除货位", notes = "删除货位", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = productAllocationService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

