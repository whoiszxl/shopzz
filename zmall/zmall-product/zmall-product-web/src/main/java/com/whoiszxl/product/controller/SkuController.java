package com.whoiszxl.product.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.product.entity.Sku;
import com.whoiszxl.product.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: SKU管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-SKU管理控制器", tags = "ZMALL-SKU管理控制器")
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @ApiOperation("查询所有的SKU")
    @GetMapping
    public Result<List<Sku>> findAll() {
        List<Sku> list = skuService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询SKU")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Sku sku = skuService.getById(id);
        return Result.success(sku);
    }

    @ApiOperation("新增SKU数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku", value = "SKU对象", required = true, dataType = "Sku", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Sku sku){
        boolean isSave = skuService.save(sku);
        return isSave ? Result.success() : Result.fail("add fail");
    }

    @ApiOperation("修改SKU数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku", value = "SKU对象", required = true, dataType = "Sku", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Sku sku, @PathVariable String id){
        sku.setId(id);
        boolean isUpdate = skuService.updateById(sku);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除SKU数据")
    @ApiImplicitParam(value = "id",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        boolean isDelete = skuService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }


    @PostMapping("/spu/{spuId}")
    public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId){
        Map<String,Object> searchMap = new HashMap<>();

        if (!"all".equals(spuId)){
            searchMap.put("spuId",spuId);
        }
        searchMap.put("status","1");
        List<Sku> skuList = skuService.findList(searchMap);
        return skuList;
    }
}
