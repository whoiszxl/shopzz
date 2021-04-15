package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.entity.dto.BrandDTO;
import com.whoiszxl.zero.entity.vo.BrandVO;
import com.whoiszxl.zero.service.BrandService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "品牌控制器")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "查找所有品牌")
    @GetMapping("/list")
    public Result<List<BrandVO>> list() {
        List<BrandDTO> brandDTOList = brandService.findAll();
        return Result.buildSuccess(BeanCopierUtils.copyListProperties(brandDTOList, BrandVO::new));
    }
}
