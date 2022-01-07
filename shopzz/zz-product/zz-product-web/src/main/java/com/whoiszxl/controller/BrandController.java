package com.whoiszxl.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.BrandShowStatusConstants;
import com.whoiszxl.entity.Brand;
import com.whoiszxl.entity.vo.BrandVO;
import com.whoiszxl.service.BrandService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌相关接口")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @ApiOperation(value = "获取所有品牌", notes = "获取所有品牌", response = Brand.class)
    public ResponseResult<List<BrandVO>> list() {
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Brand::getShowStatus, BrandShowStatusConstants.SHOW);

        List<Brand> brandList = brandService.list(wrapper);
        List<BrandVO> brandVOList = BeanCopierUtils.copyListProperties(brandList, BrandVO::new);
        return ResponseResult.buildSuccess(brandVOList);
    }

}

