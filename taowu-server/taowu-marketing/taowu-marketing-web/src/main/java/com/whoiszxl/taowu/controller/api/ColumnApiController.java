package com.whoiszxl.taowu.controller.api;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.query.ProductColumnApiQuery;
import com.whoiszxl.taowu.cqrs.response.ColumnDetailApiResponse;
import com.whoiszxl.taowu.service.ProductColumnService;
import com.whoiszxl.taowu.service.ProductColumnSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/column")
@Tag(name = "C端:专栏相关接口")
@RequiredArgsConstructor
public class ColumnApiController {

    private final ProductColumnService columnService;

    private final ProductColumnSpuService productColumnSpuService;


    @SaCheckLogin
    @PostMapping("/detail")
    @Operation(summary = "通过专栏ID获取专栏详情", description = "通过专栏ID获取专栏详情")
    public ResponseResult<ColumnDetailApiResponse> detail(@RequestBody @Validated ProductColumnApiQuery query) {
        ColumnDetailApiResponse response = columnService.detail(query.getId());
        return ResponseResult.buildSuccess(response);

    }
}

