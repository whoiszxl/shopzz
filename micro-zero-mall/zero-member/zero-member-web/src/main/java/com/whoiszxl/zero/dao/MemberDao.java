package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.entity.Member;

public interface MemberDao {

    /**
     * 通过会员Id查找会员详情
     * @param id 会员ID
     * @return
     */
    Member findById(Long id);

}
