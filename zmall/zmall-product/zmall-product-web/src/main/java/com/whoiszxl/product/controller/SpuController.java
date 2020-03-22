package com.whoiszxl.product.controller;

import com.whoiszxl.common.config.RocketMQConfig;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.product.entity.Product;
import com.whoiszxl.product.entity.Spu;
import com.whoiszxl.product.listener.RocketMQSender;
import com.whoiszxl.product.service.SkuService;
import com.whoiszxl.product.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: SPU管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-SPU管理控制器", tags = "ZMALL-SPU管理控制器", description = "ZMALL-SPU管理控制器描述")
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private RocketMQSender rocketMQSender;

    @ApiOperation("查询所有的SPU")
    @GetMapping
    public Result<List<Spu>> findAll() {
        List<Spu> list = spuService.list();
        return Result.success(list);
    }

    @ApiOperation("根据ID查询SPU")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Product product = spuService.findProductById(id);
        return Result.success(product);
    }

    @ApiOperation("新增商品数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product", value = "商品对象", required = true, dataType = "Product", paramType = "body")})
    @PostMapping
    public Result add(@RequestBody Product product){
        spuService.add(product);
        return Result.success();
    }

    @ApiOperation("修改商品数据")
    @ApiImplicitParam(name = "product", value = "商品对象", required = true, dataType = "Product", paramType = "body")
    @PutMapping
    public Result update(@RequestBody Product product){
        spuService.update(product);
        return null;
    }

    @ApiOperation("根据ID删除商品数据")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        Spu spu = spuService.getById(id);
        if(!spu.getIsMarketable().equals("0")) {
            throw new RuntimeException("必须先下架再删除！");
        }

        spu.setIsDelete("1");
        spu.setStatus("0");
        boolean isDelete = spuService.updateById(spu);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }


    @ApiOperation("审核商品")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable String id) {
        spuService.audit(id);
        //调用MQ更新es
        rocketMQSender.send(id, RocketMQConfig.PRODUCT_UP);
        return Result.success();
    }

    @ApiOperation("下架商品")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable String id) {
        spuService.pull(id);
        //调用MQ更新es
        rocketMQSender.send(id, RocketMQConfig.PRODUCT_DOWN);
        return Result.success();
    }

    @ApiOperation("上架商品")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @PutMapping("/put/{id}")
    public Result put(@PathVariable String id) {
        spuService.put(id);
        //调用MQ更新es
        rocketMQSender.send(id, RocketMQConfig.PRODUCT_UP);
        return Result.success();
    }

    @ApiOperation("恢复商品")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable String id) {
        spuService.restore(id);
        return Result.success();
    }

    @ApiOperation("物理删除商品")
    @ApiImplicitParam(value = "id",name = "id",dataType = "string",paramType = "path")
    @DeleteMapping("/realDelete/{id}")
    public Result realDelete(@PathVariable String id) {
        spuService.readDelete(id);
        return Result.success();
    }
}
