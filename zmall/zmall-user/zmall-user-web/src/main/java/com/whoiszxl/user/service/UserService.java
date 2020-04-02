package com.whoiszxl.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.user.entity.User;
import com.whoiszxl.user.entity.request.RegisterRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
public interface UserService extends IService<User> {



    /**
     * 校验当前手机输入的验证码是否正确
     * @param mobile 用户手机号
     * @param memberVerifyCode 用户输入的验证码
     * @return
     */
    boolean checkVerifyCode(String mobile, String memberVerifyCode);

    /**
     * 注册到库中
     * @param registerRequest
     */
    void registerToDb(RegisterRequest registerRequest);

    /**
     * 移除redis中的验证码
     * @param mobile 手机号
     */
    void removeVerifyInRedis(String mobile);

    /**
     * 增加用户积分
     * @param username
     * @param point
     * @return
     */
    int addUserPoints(String username, Integer point);
}
