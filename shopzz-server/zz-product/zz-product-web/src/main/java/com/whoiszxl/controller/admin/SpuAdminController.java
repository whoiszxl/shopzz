package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.SpuSaveCommand;
import com.whoiszxl.cqrs.command.SpuUpdateCommand;
import com.whoiszxl.cqrs.query.SpuQuery;
import com.whoiszxl.entity.Spu;
import com.whoiszxl.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品基础信息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/spu")
@Api(tags = "SPU后台相关接口")
public class SpuAdminController {

    @Autowired
    private SpuService spuService;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取SPU列表", notes = "分页获取SPU列表", response = Spu.class)
    public ResponseResult<IPage<Spu>> list(@RequestBody SpuQuery query) {
        LambdaQueryWrapper<Spu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(query.getCategoryId() != null) {
            lambdaQueryWrapper.eq(Spu::getCategoryId, query.getCategoryId());
        }
        if(StringUtils.isNotBlank(query.getName())) {
            lambdaQueryWrapper.eq(Spu::getName, query.getName());
        }

        Page<Spu> result = spuService.page(new Page<>(query.getPage(), query.getSize()), lambdaQueryWrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取SPU", notes = "通过主键ID获取SPU", response = Spu.class)
    public ResponseResult<Spu> getSupplierById(@PathVariable Long id) {
        Spu spu = spuService.getById(id);
        return ResponseResult.buildSuccess(spu);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增SPU", notes = "新增SPU", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated SpuSaveCommand spuSaveCommand) {
        spuService.save(spuSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新SPU", notes = "更新SPU", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody SpuUpdateCommand spuUpdateCommand) {
        spuService.update(spuUpdateCommand);
        return ResponseResult.buildSuccess();
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除SPU", notes = "删除SPU", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        spuService.remove(id);
        return ResponseResult.buildSuccess();
    }
    
}

