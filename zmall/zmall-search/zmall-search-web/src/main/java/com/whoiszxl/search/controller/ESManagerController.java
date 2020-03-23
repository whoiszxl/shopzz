package com.whoiszxl.search.controller;


import com.whoiszxl.common.entity.Result;
import com.whoiszxl.search.service.ESManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 搜索ES管理控制器
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-搜索ES管理控制器", tags = "ZMALL-搜索ES管理控制器")
@RestController
@RequestMapping("/manager")
public class ESManagerController {

    @Autowired
    private ESManagerService esManagerService;

    @ApiOperation("创建索引库结构")
    @GetMapping("/create")
    public Result create() {
        esManagerService.createMappingAndIndex();
        return Result.success();
    }

    @ApiOperation("导入全部数据")
    @GetMapping("/importAll")
    public Result importAll(){
        esManagerService.importAll();
        return Result.success();
    }

}
