package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.command.BrandSaveCommand;
import com.whoiszxl.cqrs.command.BrandUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Brand;
import com.whoiszxl.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌后台相关接口")
public class BrandController {
    
    @Autowired
    private BrandService brandService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/list")
    @ApiOperation(value = "分页获取品牌列表", notes = "分页获取品牌列表", response = Brand.class)
    public ResponseResult<IPage<Brand>> list(@RequestBody PageQuery query) {
        Page<Brand> result = brandService.page(new Page<>(query.getPage(), query.getSize()), null);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增品牌", notes = "新增品牌", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody @Validated BrandSaveCommand brandSaveCommand) {
        boolean saveFlag = brandService.save(dozerUtils.map(brandSaveCommand, Brand.class));
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新品牌", notes = "更新品牌", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody BrandUpdateCommand brandUpdateCommand) {
        boolean updateFlag = brandService.updateById(dozerUtils.map(brandUpdateCommand, Brand.class));
        return ResponseResult.buildByFlag(updateFlag);
    }


    @SaCheckLogin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除品牌", notes = "删除品牌", response = ResponseResult.class)
    public ResponseResult<Boolean> delete(@PathVariable Long id) {
        boolean removeFlag = brandService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

