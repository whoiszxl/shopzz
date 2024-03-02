package com.whoiszxl.zhipin.member.service;

import com.whoiszxl.zhipin.member.cqrs.command.SmsLoginCommand;

/**
 * <p>
 * 登录 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-04
 */
public interface ILoginService {

    /**
     * 发送短信验证码
     * @param phone 手机号
     * @return uuid
     */
    String sendSmsCaptcha(String phone);

    /**
     * 短信登录
     * @param command 登录参数
     * @return 用户token
     */
    String smsLogin(SmsLoginCommand command);
}
