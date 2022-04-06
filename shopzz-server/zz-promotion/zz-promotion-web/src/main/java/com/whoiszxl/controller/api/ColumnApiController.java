package com.whoiszxl.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.query.ProductColumnApiQuery;
import com.whoiszxl.cqrs.response.ColumnDetailApiResponse;
import com.whoiszxl.cqrs.response.ProductColumnResponse;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.service.ProductColumnService;
import com.whoiszxl.service.ProductColumnSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/column")
@Api(tags = "C端:专栏相关接口")
public class ColumnApiController {

    @Autowired
    private ProductColumnService columnService;

    @Autowired
    private ProductColumnSpuService productColumnSpuService;

    @Autowired
    private DozerUtils dozerUtils;

    @SaCheckLogin
    @PostMapping("/detail")
    @ApiOperation(value = "通过专栏ID获取专栏详情", notes = "通过专栏ID获取专栏详情", response = ProductColumnResponse.class)
    public ResponseResult<ColumnDetailApiResponse> detail(@RequestBody @Validated ProductColumnApiQuery query) {
        ColumnDetailApiResponse response = columnService.detail(query.getId());
        return ResponseResult.buildSuccess(response);

    }
}

