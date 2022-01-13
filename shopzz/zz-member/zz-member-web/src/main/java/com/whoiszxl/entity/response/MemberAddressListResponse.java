package com.whoiszxl.entity.response;

import com.whoiszxl.entity.vo.MemberAddressVO;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author whoiszxl
 * @date 2022/1/12
 */
@Data
public class MemberAddressListResponse {

    //主地址
    private MemberAddressVO mainAddress;

    //其他地址
    private List<MemberAddressVO> addressList;



}
