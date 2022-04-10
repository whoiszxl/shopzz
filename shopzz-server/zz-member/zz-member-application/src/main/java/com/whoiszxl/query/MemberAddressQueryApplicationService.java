package com.whoiszxl.query;

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
}
