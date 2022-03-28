package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SkuSaveCommand;
import com.whoiszxl.cqrs.query.SkuQuery;
import com.whoiszxl.entity.Sku;
import com.whoiszxl.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku信息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/sku")
@Api(tags = "SKU后台相关接口")
public class SkuAdminController {

    @Autowired
    private SkuService skuService;

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增SKU", notes = "新增SKU", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated SkuSaveCommand skuSaveCommand) {
        skuService.save(skuSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取SPU列表", notes = "分页获取SPU列表", response = Sku.class)
    public ResponseResult<IPage<Sku>> list(@RequestBody SkuQuery query) {
        LambdaQueryWrapper<Sku> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(query.getSpuId() != null) {
            lambdaQueryWrapper.eq(Sku::getSpuId, query.getSpuId());
        }
        if(StringUtils.isNotBlank(query.getName())) {
            lambdaQueryWrapper.like(Sku::getSkuName, "%" + query.getName() + "%");
        }

        Page<Sku> result = skuService.page(new Page<>(query.getPage(), query.getSize()), lambdaQueryWrapper);
        return ResponseResult.buildSuccess(result);
    }
}

