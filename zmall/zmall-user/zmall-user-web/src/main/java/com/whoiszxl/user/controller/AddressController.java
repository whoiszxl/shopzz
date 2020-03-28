package com.whoiszxl.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.user.config.TokenDecode;
import com.whoiszxl.user.entity.Address;
import com.whoiszxl.user.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Api(value = "ZMALL-地址管理控制器", tags = "ZMALL-地址管理控制器")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private TokenDecode tokenDecode;

    @ApiOperation("查询所有的地址")
    @GetMapping
    public Result<List<Address>> findAll() {
        String username = tokenDecode.getUsername();
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<Address> list = addressService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation("根据ID查询地址")
    @ApiImplicitParam(value = "地址ID",name = "id",dataType = "integer",paramType = "path")
    @GetMapping("/{id}")
    public Result<Address> findById(@PathVariable Integer id){
        Address address = addressService.getById(id);
        return Result.success(address);
    }

    @ApiOperation("新增地址数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址对象", required = true, dataType = "Address", paramType = "body")})
    @PostMapping
    public Result addressd(@RequestBody Address address){
        boolean isSave = addressService.save(address);
        return isSave ? Result.success() : Result.fail("addressd fail");
    }

    @ApiOperation("修改地址数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "地址对象", required = true, dataType = "Address", paramType = "body"),
            @ApiImplicitParam(name = "id", value = "地址ID", dataType = "integer",paramType = "path")})
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Address address, @PathVariable Integer id){
        address.setId(id);
        boolean isUpdate = addressService.updateById(address);
        return isUpdate ? Result.success() : Result.fail("update fail");
    }

    @ApiOperation("根据ID删除地址数据")
    @ApiImplicitParam(value = "地址ID",name = "id",dataType = "integer",paramType = "path")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        boolean isDelete = addressService.removeById(id);
        return isDelete ? Result.success() : Result.fail("delete fail");
    }
}
