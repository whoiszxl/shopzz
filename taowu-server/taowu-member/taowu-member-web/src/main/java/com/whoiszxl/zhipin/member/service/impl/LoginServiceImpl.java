package com.whoiszxl.zhipin.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.AppLoginMember;
import com.whoiszxl.taowu.common.utils.IpUtils;
import com.whoiszxl.taowu.common.utils.MyServletUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.zhipin.member.cqrs.command.SmsLoginCommand;
import com.whoiszxl.zhipin.member.entity.Member;
import com.whoiszxl.zhipin.member.service.ILoginService;
import com.whoiszxl.zhipin.member.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {

    private final RedisUtils redisUtils;

    private final IMemberService memberService;

    private final TokenHelper tokenHelper;

    @Override
    public String sendSmsCaptcha(String phone) {
        // 从Redis中获取对应手机号的验证码信息
        String captchaKey = RedisPrefixConstants.format(RedisPrefixConstants.Member.MEMBER_CAPTCHA_SMS, phone);
        String captchaValue = redisUtils.get(captchaKey);

        // 判断是否是在120秒内重复发送
        if(StrUtil.isNotBlank(captchaValue)) {
            long ttl = Long.parseLong(captchaValue.split(RedisPrefixConstants.Member.MEMBER_CAPTCHA_SMS_SEPARATOR)[0]);
            if(DateUtil.currentSeconds() - ttl < RedisPrefixConstants.Member.SMS_TIMEOUT) {
                ExceptionCatcher.catchServiceEx("你发送得太快啦");
            }
        }

        // 生成验证码
        int smsCode = RandomUtil.randomInt(1000, 9999);
        String uuid = UUID.fastUUID().toString();
        String value = DateUtil.currentSeconds()
                + RedisPrefixConstants.Member.MEMBER_CAPTCHA_SMS_SEPARATOR
                + smsCode
                + RedisPrefixConstants.Member.MEMBER_CAPTCHA_SMS_SEPARATOR
                + uuid;

        // 将验证码保存到 Redis，有效期 10 分钟
        redisUtils.setEx(captchaKey, value, 10, TimeUnit.MINUTES);

        //3. 调用第三方平台发送短信
        log.info("短信发送成功: {}", smsCode);

        //3. 返回结果
        return uuid;
    }

    @Override
    public String smsLogin(SmsLoginCommand command) {
        // 校验验证码是否正确
        String captchaKey = RedisPrefixConstants.format(RedisPrefixConstants.Member.MEMBER_CAPTCHA_SMS, command.getPhone());
        String captchaValue = redisUtils.get(captchaKey);
        if(StrUtil.isBlank(captchaValue)) {
            ExceptionCatcher.catchServiceEx("参数有误");
        }

        String[] valueParams = captchaValue.split(RedisPrefixConstants.Member.MEMBER_CAPTCHA_SMS_SEPARATOR);
        Assert.isTrue(valueParams.length == 3, "参数有误");
        Assert.isTrue(StrUtil.equals(command.getSmsCode(), valueParams[1]), "验证码输入错误");
        Assert.isTrue(StrUtil.equals(command.getUuid(), valueParams[2]), "UUID输入错误");

        // 如果用户已注册，则直接登录，未注册则进行注册
        Member member = memberService.getOne(Wrappers.<Member>lambdaQuery()
                .eq(Member::getPhone, command.getPhone()));

        if(member == null) {
            //进行注册
            member = new Member();
            member.setId(IdUtil.getSnowflakeNextId());
            member.setPhone(command.getPhone());

            HttpServletRequest request = MyServletUtil.getRequest();
            member.setIp(ServletUtil.getClientIP(request));
            member.setCity(IpUtils.getCityInfo(member.getIp()));
            memberService.save(member);
        }

        //进行登录
        AppLoginMember appLoginMember = BeanUtil.copyProperties(member, AppLoginMember.class);
        tokenHelper.appLogin(appLoginMember);

        //登录成功后删除验证码缓存
        redisUtils.delete(captchaKey);

        return StpUtil.getTokenValue();
    }
}
