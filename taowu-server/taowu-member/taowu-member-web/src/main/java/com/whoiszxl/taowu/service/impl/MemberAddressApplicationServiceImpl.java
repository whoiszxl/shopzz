package com.whoiszxl.taowu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import com.whoiszxl.taowu.service.MemberAddressApplicationService;
import com.whoiszxl.taowu.cqrs.command.MemberAddressSaveCommand;
import com.whoiszxl.taowu.cqrs.command.MemberAddressUpdateCommand;
import com.whoiszxl.taowu.cqrs.response.MemberAddressResponse;
import com.whoiszxl.taowu.entity.MemberAddress;
import com.whoiszxl.taowu.mapper.MemberAddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员应用服务实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAddressApplicationServiceImpl implements MemberAddressApplicationService {


    private final MemberAddressMapper memberAddressMapper;

    private final IdWorker idWorker;

    private final TokenHelper tokenHelper;

    @Override
    public void removeById(Long id) {
        MemberAddress memberAddress = memberAddressMapper.selectById(id);
        if(StpUtil.getLoginIdAsLong() != memberAddress.getMemberId()) {
            ExceptionCatcher.catchServiceEx("无权限删除");
        }
        memberAddressMapper.deleteById(id);
    }

    @Override
    public void saveAddress(MemberAddressSaveCommand memberAddressSaveCommand) {
        MemberAddress memberAddress = BeanUtil.copyProperties(memberAddressSaveCommand, MemberAddress.class);
        memberAddress.setId(idWorker.nextId());
        memberAddress.setMemberId(tokenHelper.getAppMemberId());
        memberAddressMapper.insert(memberAddress);
    }

    @Override
    public void updateAddress(MemberAddressUpdateCommand memberAddressUpdateCommand) {
        MemberAddress memberAddress = BeanUtil.copyProperties(memberAddressUpdateCommand, MemberAddress.class);
        memberAddress.setMemberId(StpUtil.getLoginIdAsLong());
        memberAddressMapper.updateById(memberAddress);
    }

    @Override
    public List<MemberAddressResponse> list() {
        long memberId = tokenHelper.getAppMemberId();
        List<MemberAddress> memberAddressList = memberAddressMapper.selectList(
                Wrappers.<MemberAddress>lambdaQuery()
                        .eq(MemberAddress::getMemberId, memberId)
                        .orderByDesc(MemberAddress::getUpdatedAt)
                        .orderByDesc(MemberAddress::getIsDefault)
        );

        return BeanUtil.copyToList(memberAddressList, MemberAddressResponse.class);
    }

    @Override
    public MemberAddressFeignDTO getMemberAddress(Long memberId, Long addressId) {
        LambdaQueryWrapper<MemberAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberAddress::getMemberId, memberId);
        queryWrapper.eq(MemberAddress::getId, addressId);
        MemberAddress memberAddress = memberAddressMapper.selectOne(queryWrapper);
        MemberAddressFeignDTO memberAddressFeignDTO = BeanUtil.copyProperties(memberAddress, MemberAddressFeignDTO.class);
        return memberAddressFeignDTO;
    }
}
