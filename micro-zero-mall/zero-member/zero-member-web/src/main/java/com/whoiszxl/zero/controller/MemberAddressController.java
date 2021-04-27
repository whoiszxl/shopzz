package com.whoiszxl.zero.controller;

import com.whoiszxl.zero.bean.Result;
import com.whoiszxl.zero.entity.dto.MemberAddressDTO;
import com.whoiszxl.zero.feign.MemberAddressFeign;
import com.whoiszxl.zero.vo.MemberAddressVO;
import com.whoiszxl.zero.service.MemberAddressService;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "会员地址控制器")
@RestController
@RequestMapping("/address")
public class MemberAddressController implements MemberAddressFeign {

    @Autowired
    private MemberAddressService memberAddressService;

    @Override
    @ApiOperation("收货地址列表")
    @GetMapping("/list")
    public Result<List<MemberAddressVO>> list() {
        List<MemberAddressDTO> memberAddressDTOS = memberAddressService.list();
        List<MemberAddressVO> result = ObjectUtils.isEmpty(memberAddressDTOS) ? null : BeanCopierUtils.copyListProperties(memberAddressDTOS, MemberAddressVO::new);
        return Result.buildSuccess(result);
    }

    @ApiOperation("收货地址保存")
    @PostMapping("/save")
    public Result save(@RequestBody MemberAddressVO memberAddressVO) {
        memberAddressService.save(memberAddressVO.clone(MemberAddressDTO.class));
        return Result.buildSuccess();
    }


    @ApiOperation("收货地址修改")
    @PostMapping("/update")
    public Result update(@RequestBody MemberAddressVO memberAddressVO) {
        memberAddressService.update(memberAddressVO.clone(MemberAddressDTO.class));
        return Result.buildSuccess();
    }

    @ApiOperation("收货地址删除")
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        memberAddressService.delete(id);
        return Result.buildSuccess();
    }

}
