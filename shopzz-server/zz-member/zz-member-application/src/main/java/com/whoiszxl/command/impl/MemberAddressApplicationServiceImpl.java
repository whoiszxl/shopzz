package com.whoiszxl.command.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.whoiszxl.aggregate.model.MemberAddress;
import com.whoiszxl.aggregate.repository.MemberAddressRepository;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.command.MemberAddressApplicationService;
import com.whoiszxl.command.cmd.MemberAddressSaveCommand;
import com.whoiszxl.command.cmd.MemberAddressUpdateCommand;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员应用服务实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Slf4j
@Service
public class MemberAddressApplicationServiceImpl implements MemberAddressApplicationService {

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public void removeById(Long id) {
        MemberAddress memberAddress = memberAddressRepository.byId(id);
        if(StpUtil.getLoginIdAsLong() != memberAddress.getMemberId()) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("无权限删除"));
        }
        memberAddressRepository.delete(id);
    }

    @Override
    public void saveAddress(MemberAddressSaveCommand memberAddressSaveCommand) {
        MemberAddress memberAddress = dozerUtils.map(memberAddressSaveCommand, MemberAddress.class);
        memberAddress.setMemberId(AuthUtils.getMemberId());
        memberAddressRepository.save(memberAddress);
    }

    @Override
    public void updateAddress(MemberAddressUpdateCommand memberAddressUpdateCommand) {
        MemberAddress memberAddress = dozerUtils.map(memberAddressUpdateCommand, MemberAddress.class);
        memberAddress.setMemberId(StpUtil.getLoginIdAsLong());
    }
}
