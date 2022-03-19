package com.whoiszxl.service;

import com.whoiszxl.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.vo.MemberDetailVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-29
 */
public interface MemberService extends IService<Member> {

    /**
     * 查询当前登录用户的会员信息
     * @return 会员信息
     */
    MemberDetailVO memberInfo();
}
