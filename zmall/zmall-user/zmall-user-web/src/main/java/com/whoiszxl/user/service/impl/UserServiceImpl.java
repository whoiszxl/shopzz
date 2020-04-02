package com.whoiszxl.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.common.constant.BooleanEnum;
import com.whoiszxl.common.constant.UserRedisPrefixEnum;
import com.whoiszxl.common.utils.RedisUtils;
import com.whoiszxl.user.entity.User;
import com.whoiszxl.user.entity.request.RegisterRequest;
import com.whoiszxl.user.mapper.UserMapper;
import com.whoiszxl.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public boolean checkVerifyCode(String mobile, String memberVerifyCode) {
        String redisVerifyCode = redisUtils.get(UserRedisPrefixEnum.USER_REGISTER_PHONE_CODE.getKey() + mobile);
        return StringUtils.equals(memberVerifyCode, redisVerifyCode);
    }

    @Override
    public void registerToDb(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getMobile());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setPhone(registerRequest.getMobile());
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setNickName(registerRequest.getMobile());
        user.setStatus(BooleanEnum.IS_TRUE.getBool());
        user.setIsMobileCheck(BooleanEnum.IS_TRUE.getBool());
        user.setIsEmailCheck(BooleanEnum.IS_FALSE.getBool());
        userMapper.insert(user);
    }

    @Override
    public void removeVerifyInRedis(String mobile) {
        redisUtils.delete(UserRedisPrefixEnum.USER_REGISTER_PHONE_CODE.getKey() + mobile);
    }

    @Override
    public int addUserPoints(String username, Integer point) {
        return userMapper.updateUserPoint(username, point);
    }
}
