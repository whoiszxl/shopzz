package com.whoiszxl.product.controller;

import com.whoiszxl.product.entity.Sku;
import com.whoiszxl.product.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-测试控制器", tags = "ZMALL-测试控制器tags", description = "ZMALL-测试控制器描述")
@RestController
public class TestController {

    @Value("${show.name}")
    private String showName;

    @Autowired
    private SkuService mallSkuService;

    @ApiOperation("测试查询所有sku")
    @ApiImplicitParam(name = "skuId", value = "sku ID", required = true, dataType = "string")
    @GetMapping("/test")
    public Sku test(@RequestParam String skuId) {
        return mallSkuService.getById(skuId);
    }
}
