package com.whoiszxl.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.common.entity.PageResult;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.product.entity.Brand;
import com.whoiszxl.product.entity.vo.BrandSearchVo;
import com.whoiszxl.product.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 品牌管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-品牌管理控制器", tags = "ZMALL-品牌管理控制器tags", description = "ZMALL-品牌管理控制器描述")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService mallBrandService;

    @ApiOperation("查询所有的品牌")
    @GetMapping
    public Result<List<Brand>> findAll() {
        List<Brand> list = mallBrandService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询品牌")
    @ApiImplicitParam(value = "品牌ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Brand brand = mallBrandService.getById(id);
        return Result.success(brand);
    }

    @ApiOperation("新增品牌数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brand", value = "品牌对象", required = true, dataType = "Brand", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Brand brand){
        boolean isSave = mallBrandService.save(brand);
        return isSave ? Result.success() : Result.fail("add fail");
    }

    @ApiOperation("修改品牌数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brand", value = "品牌对象", required = true, dataType = "Brand", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "品牌ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable Integer id){
        brand.setId(id);
        boolean isUpdate = mallBrandService.updateById(brand);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除品牌数据")
    @ApiImplicitParam(value = "品牌ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = mallBrandService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }

    @ApiOperation("多条件搜索品牌数据")
    @ApiImplicitParam(value = "搜索条件",name = "brandSearchVo",dataType = "BrandSearchVo",paramType = "body")
    @PostMapping(value = "/search" )
    public Result findList(@RequestBody BrandSearchVo brandSearchVo){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(brandSearchVo.getName())) {
            queryWrapper.like("name", "%" + brandSearchVo.getName() + "%");
        }
        if(StringUtils.isNotBlank(brandSearchVo.getLetter())) {
            queryWrapper.like("letter", "%" + brandSearchVo.getLetter() + "%");
        }
        List list = mallBrandService.list(queryWrapper);
        return Result.success(list);
    }


    @ApiOperation("分页搜索实现")
    @ApiImplicitParam(value = "搜索条件",name = "brandSearchVo",dataType = "BrandSearchVo",paramType = "body")
    @PostMapping(value = "/searchByPage" )
    public Result<PageResult> findPage(@RequestBody BrandSearchVo brandSearchVo){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(brandSearchVo.getName())) {
            queryWrapper.like("name", "%" + brandSearchVo.getName() + "%");
        }
        if(StringUtils.isNotBlank(brandSearchVo.getLetter())) {
            queryWrapper.like("letter", "%" + brandSearchVo.getLetter() + "%");
        }

        IPage<Brand> iPage = new Page<>(brandSearchVo.getPage(), brandSearchVo.getSize());
        IPage page1 = mallBrandService.page(iPage, queryWrapper);
        PageResult pageResult=new PageResult(page1.getTotal(), page1.getRecords());
        return Result.success(pageResult);
    }

    @ApiOperation("根据分类名称查询品牌列表")
    @ApiImplicitParam(value = "分类名称",name = "categoryName",dataType = "string",paramType = "path")
    @GetMapping("/category/{categoryName}")
    public Result<List<Map>> findBrandListByCategoryName(@PathVariable("categoryName")String categoryName){
        List<Map> brandList = mallBrandService.findBrandListByCategoryName(categoryName);
        return Result.success(brandList);
    }
}
