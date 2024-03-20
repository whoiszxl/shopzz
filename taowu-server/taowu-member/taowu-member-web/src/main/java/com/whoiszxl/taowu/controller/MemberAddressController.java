package com.whoiszxl.taowu.controller;


import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.MemberAddressSaveCommand;
import com.whoiszxl.taowu.cqrs.command.MemberAddressUpdateCommand;
import com.whoiszxl.taowu.cqrs.response.MemberAddressResponse;
import com.whoiszxl.taowu.service.MemberAddressApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
@RestController
@RequestMapping("/api/address")
@Tag(name = "C端:用户地址相关 API")
@RequiredArgsConstructor
public class MemberAddressController {


    private final MemberAddressApplicationService memberAddressApplicationService;


    @GetMapping
    @Operation(summary = "查询当前用户的收货地址列表", description = "查询当前用户的收货地址列表")
    public ResponseResult<List<MemberAddressResponse>> list() {
        List<MemberAddressResponse> result = memberAddressApplicationService.list();
        return ResponseResult.buildSuccess(result);
    }

    @PostMapping
    @Operation(summary = "新增收货地址", description = "新增收货地址")
    public ResponseResult<Boolean> save(@RequestBody MemberAddressSaveCommand memberAddressSaveCommand) {
        memberAddressApplicationService.saveAddress(memberAddressSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @PutMapping
    @Operation(summary = "更新收货地址", description = "更新收货地址")
    public ResponseResult update(@RequestBody MemberAddressUpdateCommand memberAddressUpdateCommand) {
        memberAddressApplicationService.updateAddress(memberAddressUpdateCommand);
        return ResponseResult.buildSuccess();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除收货地址", description = "删除收货地址")
    public ResponseResult delete(@PathVariable Long id) {
        memberAddressApplicationService.removeById(id);
        return ResponseResult.buildSuccess();
    }

}

