package com.whoiszxl.taowu.cqrs.response;

import lombok.Data;

import java.util.List;

/**
 * 用户地址列表
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@Data
public class MemberAddressListResponse {

    //主地址
    private MemberAddressResponse mainAddress;

    //其他地址
    private List<MemberAddressResponse> addressList;



}
