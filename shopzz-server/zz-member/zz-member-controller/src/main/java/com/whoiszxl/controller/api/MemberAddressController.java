package com.whoiszxl.controller.api;


import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.command.MemberAddressApplicationService;
import com.whoiszxl.command.cmd.MemberAddressSaveCommand;
import com.whoiszxl.command.cmd.MemberAddressUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.query.MemberAddressQueryApplicationService;
import com.whoiszxl.query.model.response.MemberAddressListResponse;
import com.whoiszxl.query.model.response.MemberAddressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "用户地址相关接口")
public class MemberAddressController {

    @Autowired
    private MemberAddressQueryApplicationService memberAddressQueryApplicationService;

    @Autowired
    private MemberAddressApplicationService memberAddressApplicationService;


    @GetMapping
    @ApiOperation(value = "查询当前用户的收货地址列表", notes = "查询当前用户的收货地址列表", response = MemberAddressVO.class)
    public ResponseResult<MemberAddressListResponse> list() {
        MemberAddressListResponse response = memberAddressQueryApplicationService.list();
        return ResponseResult.buildSuccess(response);
    }

    @PostMapping
    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", response = Boolean.class)
    public ResponseResult<Boolean> save(@RequestBody MemberAddressSaveCommand memberAddressSaveCommand) {
        memberAddressApplicationService.saveAddress(memberAddressSaveCommand);
        return ResponseResult.buildSuccess();
    }

    @PutMapping
    @ApiOperation(value = "更新收货地址", notes = "更新收货地址", response = Boolean.class)
    public ResponseResult update(@RequestBody MemberAddressUpdateCommand memberAddressUpdateCommand) {
        memberAddressApplicationService.updateAddress(memberAddressUpdateCommand);
        return ResponseResult.buildSuccess();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", response = Boolean.class)
    public ResponseResult delete(@PathVariable Long id) {
        memberAddressApplicationService.removeById(id);
        return ResponseResult.buildSuccess();
    }

}

