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

    @PostMapping("/decr/count")
    public Result decrCount(@RequestParam("username") String username){
        skuService.decrCount(username);
        return Result.success();
    }
}
