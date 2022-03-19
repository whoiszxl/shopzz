package com.whoiszxl.controller.admin;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.Brand;
import com.whoiszxl.entity.query.BrandQuery;
import com.whoiszxl.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Slf4j
@RestController
@RequestMapping("/admin/brand")
@Api(tags = "品牌相关管理员接口")
public class BrandAdminController {

    @Autowired
    private BrandService brandService;

    @SaCheckLogin
    @GetMapping
    @ApiOperation(value = "分页获取品牌列表", notes = "分页获取品牌列表", response = Brand.class)
    public ResponseResult<IPage<Brand>> list(BrandQuery query) {
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(query.getBrandName())) {
            wrapper.like(Brand::getChineseName, "%" + query.getBrandName() + "%")
                    .or().like(Brand::getEnglishName, "%" + query.getBrandName() + "%")
                    .or().like(Brand::getAliasName, "%" + query.getBrandName() + "%");
        }

        if(StringUtils.isNotBlank(query.getFirstLetter())) {
            wrapper.eq(Brand::getFirstLetter, query.getFirstLetter());
        }

        if(query.getShowStatus() != null) {
            wrapper.eq(Brand::getShowStatus, query.getShowStatus());
        }

        IPage<Brand> result = brandService.page(new Page<>(query.getPage(), query.getSize()), wrapper);
        return ResponseResult.buildSuccess(result);
    }

    @SaCheckLogin
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键ID获取品牌信息", notes = "通过主键ID获取品牌信息", response = Brand.class)
    public ResponseResult<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getById(id);
        return brand == null ? ResponseResult.buildSuccess() : ResponseResult.buildSuccess(brand);
    }

    @SaCheckLogin
    @PostMapping
    @ApiOperation(value = "新增品牌", notes = "新增品牌", response = ResponseResult.class)
    public ResponseResult<Boolean> save(@RequestBody Brand brand) {
        boolean saveFlag = brandService.save(brand);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @SaCheckLogin
    @PutMapping
    @ApiOperation(value = "更新品牌", notes = "更新品牌", response = ResponseResult.class)
    public ResponseResult<Boolean> update(@RequestBody Brand brand) {
        boolean updateFlag = brandService.updateById(brand);
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

