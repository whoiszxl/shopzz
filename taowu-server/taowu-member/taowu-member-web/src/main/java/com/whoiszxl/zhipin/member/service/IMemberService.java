package com.whoiszxl.zhipin.member.service;

import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.zhipin.member.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-04
 */
public interface IMemberService extends IService<Member> {

    /**
     * 通过用户ID获取用户的详细信息
     * @param memberIdList
     * @return
     */
    List<MemberDTO> findMemberInfoByIds(List<Long> memberIdList);
}
