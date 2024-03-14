package com.whoiszxl.zhipin.member.service;

import com.whoiszxl.taowu.member.dto.MemberAddressFeignDTO;
import com.whoiszxl.zhipin.member.cqrs.command.MemberAddressSaveCommand;
import com.whoiszxl.zhipin.member.cqrs.command.MemberAddressUpdateCommand;
import com.whoiszxl.zhipin.member.cqrs.response.MemberAddressResponse;

import java.util.List;

/**
 * 会员地址应用服务
 *
 * 主要是对业务逻辑进行编排，仅对领域服务与领域模型进行编排
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
public interface MemberAddressApplicationService {

    /**
     * 通过地址ID删除地址
     * @param id 地址ID
     */
    void removeById(Long id);

    /**
     * 保存地址
     * @param memberAddressSaveCommand 地址保存命令
     */
    void saveAddress(MemberAddressSaveCommand memberAddressSaveCommand);

    /**
     * 更新地址
     * @param memberAddressUpdateCommand 地址更新命令
     */
    void updateAddress(MemberAddressUpdateCommand memberAddressUpdateCommand);


    /**
     * 获取当前登录用户的地址列表
     * @return
     */
    List<MemberAddressResponse> list();

    /**
     * 获取用户地址
     * @param memberId 用户ID
     * @param addressId 地址ID
     * @return
     */
    MemberAddressFeignDTO getMemberAddress(Long memberId, Long addressId);

}
