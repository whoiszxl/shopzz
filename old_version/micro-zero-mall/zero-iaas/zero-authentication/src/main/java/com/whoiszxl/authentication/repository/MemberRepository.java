package com.whoiszxl.authentication.repository;

import com.whoiszxl.authentication.entity.Member;
import org.springframework.stereotype.Repository;

/**
 * 会员repository服务
 *
 * @author whoiszxl
 * @date 2021/4/8
 */
@Repository
public interface MemberRepository extends BaseRepository<Member> {

    /**
     * 通过用户名或手机号或邮箱查找用户信息
     * @param username 用户名
     * @param phone 手机号
     * @param email 邮箱
     * @param status 状态
     * @return
     */
    Member findByUsernameOrPhoneOrEmailAndStatus(String username, String phone, String email, Integer status);
}
