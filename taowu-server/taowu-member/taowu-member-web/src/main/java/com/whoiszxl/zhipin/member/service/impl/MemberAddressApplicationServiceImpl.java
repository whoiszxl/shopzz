package com.whoiszxl.zhipin.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import com.whoiszxl.zhipin.member.cqrs.command.MemberAddressSaveCommand;
import com.whoiszxl.zhipin.member.cqrs.command.MemberAddressUpdateCommand;
import com.whoiszxl.zhipin.member.cqrs.response.MemberAddressResponse;
import com.whoiszxl.zhipin.member.entity.MemberAddress;
import com.whoiszxl.zhipin.member.mapper.MemberAddressMapper;
import com.whoiszxl.zhipin.member.service.MemberAddressApplicationService;
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
        return null;
    }
}
