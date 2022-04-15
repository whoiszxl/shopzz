package com.whoiszxl.query;

import com.whoiszxl.dto.MemberAddressDTO;
import com.whoiszxl.dto.MemberAddressFeignDTO;
import com.whoiszxl.query.model.response.MemberAddressListResponse;

/**
 * 用户地址查询应用服务接口
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
public interface MemberAddressQueryApplicationService {


    /**
     * 获取当前登录用户的地址列表
     * @return
     */
    MemberAddressListResponse list();

    /**
     * 获取用户地址
     * @param memberId 用户ID
     * @param addressId 地址ID
     * @return
     */
    MemberAddressFeignDTO getMemberAddress(Long memberId, Long addressId);
}
