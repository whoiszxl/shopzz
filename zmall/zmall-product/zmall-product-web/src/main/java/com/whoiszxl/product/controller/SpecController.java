package com.whoiszxl.product.controller;

import com.whoiszxl.common.entity.Result;
import com.whoiszxl.product.service.SpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-20
 **/
@Api(value = "ZMALL-SPEC管理控制器", tags = "ZMALL-SPEC管理控制器", description = "ZMALL-SPEC管理控制器描述")
@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @ApiOperation("根据分类名称查询Spec")
    @ApiImplicitParam(value = "分类名称",name = "categoryName",dataType = "string",paramType = "path")
    @GetMapping("/category/{categoryName}")
    public Result findSpecListByCategoryName(@PathVariable String categoryName) {
        List<Map> specList = specService.findSpecListByCategoryName(categoryName);
        return Result.success(specList);
    }

}
