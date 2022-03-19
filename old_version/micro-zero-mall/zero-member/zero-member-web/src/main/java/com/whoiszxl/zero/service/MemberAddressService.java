package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.MemberAddress;
import com.whoiszxl.zero.entity.dto.MemberAddressDTO;

import java.util.List;

public interface MemberAddressService {

    /**
     * 获取列表
     * @return
     */
    List<MemberAddressDTO> list();

    /**
     * 新增
     * @param memberAddressVO
     */
    void save(MemberAddressDTO memberAddressDTO);

    /**
     * 更新
     * @param memberAddressVO
     */
    void update(MemberAddressDTO memberAddressDTO);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);
}
