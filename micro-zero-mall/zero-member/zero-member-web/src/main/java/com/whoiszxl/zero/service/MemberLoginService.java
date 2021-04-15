package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.params.LoginParam;
import com.whoiszxl.zero.entity.vo.LoginMemberVO;

public interface MemberLoginService {

    /**
     * 会员登录
     * @param loginParam 登录参数
     * @return
     */
    LoginMemberVO login(LoginParam loginParam);
}
