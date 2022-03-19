package com.whoiszxl.zero.service;

import com.whoiszxl.zero.entity.params.LoginParam;
import com.whoiszxl.zero.entity.vo.LoginVO;

public interface SysLoginService {

    /**
     * 登录操作
     * @param loginParam 登录的账号密码
     * @return
     */
    LoginVO login(LoginParam loginParam);
}
