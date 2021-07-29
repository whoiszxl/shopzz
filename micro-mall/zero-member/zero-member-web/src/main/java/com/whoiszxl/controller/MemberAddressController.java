package com.whoiszxl.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.entity.MemberAddress;
import com.whoiszxl.entity.vo.MemberAddressVO;
import com.whoiszxl.service.MemberAddressService;
import com.whoiszxl.utils.BeanCopierUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/member-address")
@Api(tags = "用户地址相关接口")
public class MemberAddressController {

    @Autowired
    private MemberAddressService memberAddressService;

    @GetMapping
    @ApiOperation(value = "查询当前用户的收货地址列表", notes = "查询当前用户的收货地址列表", response = MemberAddressVO.class)
    public ResponseResult<List<MemberAddressVO>> list() {
        LambdaQueryWrapper<MemberAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddress::getMemberId, StpUtil.getLoginIdAsLong());
        List<MemberAddress> memberAddressList = memberAddressService.list(queryWrapper);
        List<MemberAddressVO> memberAddressVOList = BeanCopierUtils.copyListProperties(memberAddressList, MemberAddressVO::new);
        return ResponseResult.buildSuccess(memberAddressVOList);
    }

    @PostMapping
    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", response = Boolean.class)
    public ResponseResult<Boolean> save(@RequestBody MemberAddressVO memberAddressVO) {
        MemberAddress memberAddress = memberAddressVO.clone(MemberAddress.class);
        memberAddress.setMemberId(StpUtil.getLoginIdAsLong());
        boolean saveFlag = memberAddressService.save(memberAddress);
        return ResponseResult.buildByFlag(saveFlag);
    }

    @PutMapping
    @ApiOperation(value = "更新收货地址", notes = "更新收货地址", response = Boolean.class)
    public ResponseResult update(@RequestBody MemberAddressVO memberAddressVO) {
        MemberAddress memberAddress = memberAddressVO.clone(MemberAddress.class);
        memberAddress.setMemberId(StpUtil.getLoginIdAsLong());
        boolean updateFlag = memberAddressService.updateById(memberAddress);
        return ResponseResult.buildByFlag(updateFlag);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", response = Boolean.class)
    public ResponseResult delete(@PathVariable Long id) {
        MemberAddress memberAddress = memberAddressService.getById(id);
        if(StpUtil.getLoginIdAsLong() != memberAddress.getMemberId()) {
            return ResponseResult.buildError("无权限删除");
        }
        boolean removeFlag = memberAddressService.removeById(id);
        return ResponseResult.buildByFlag(removeFlag);
    }

}

