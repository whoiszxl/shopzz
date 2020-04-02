package com.whoiszxl.common.sms;

import com.whoiszxl.common.config.MqTopicEnums;
import com.whoiszxl.common.constant.UserRedisPrefixEnum;
import com.whoiszxl.common.entity.Result;
import com.whoiszxl.common.listener.RocketMQSender;
import com.whoiszxl.common.utils.RandomUtils;
import com.whoiszxl.common.utils.RedisUtils;
import com.whoiszxl.common.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 短信验证码发动类
 * @author: whoiszxl
 * @create: 2020-01-09
 **/
@Component
public class SmsSender {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RocketMQSender rocketMQSender;


    /**
     * 发送短信验证码
     * @param mobile 手机号码
     * @param isMock 是否真实发送,测试环境用true
     */
    public Result sendVerifySms(String mobile, boolean isMock) {
        //对手机号进行校验
        if(!RegexUtils.checkPhone(mobile)) {
            return Result.fail("手机号不正确");
        }
        //生成验证码
        String code = RandomUtils.generateNumberString(6);
        //存入redis
        redisUtils.setEx(UserRedisPrefixEnum.USER_REGISTER_PHONE_CODE.getKey() + mobile,
                code,
                UserRedisPrefixEnum.USER_REGISTER_PHONE_CODE.getTime(),
                UserRedisPrefixEnum.USER_REGISTER_PHONE_CODE.getUnit());

        if(!isMock) {
            //发送到MQ
            rocketMQSender.send(MqTopicEnums.MESSAGE_SMS_TOPIC.getName(), mobile + ":::::" + code);
        }
        return Result.success();
    }
}