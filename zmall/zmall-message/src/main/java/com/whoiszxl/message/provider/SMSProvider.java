package com.whoiszxl.message.provider;

import com.whoiszxl.common.entity.Result;

/**
 * @description: SMS供应者接口定义
 * @author: whoiszxl
 * @create: 2020-04-02
 **/
public interface SMSProvider {

    /**
     * 发送单条短信
     * @param mobile 手机号
     * @param templateCode 模板号
     * @param params 手机号
     * @return
     * @throws Exception
     */
    Result sendSingleMessage(String mobile, String templateCode, String params) throws Exception;


    /**
     * 发送验证码短信
     *
     * @param mobile     手机号
     * @param verifyCode 验证码
     * @return
     * @throws Exception
     */
    Result sendVerifyMessage(String mobile, String verifyCode);

    /**
     * 发送国际短信
     *
     * @param content
     * @param phone
     * @return
     */
    default Result sendInternationalMessage(String content, String phone) throws Exception {
        return null;
    }

}
