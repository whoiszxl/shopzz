package com.whoiszxl.command;

import com.whoiszxl.command.cmd.MemberAddressSaveCommand;
import com.whoiszxl.command.cmd.MemberAddressUpdateCommand;

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

}
