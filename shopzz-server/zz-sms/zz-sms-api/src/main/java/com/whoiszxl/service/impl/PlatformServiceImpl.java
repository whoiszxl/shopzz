package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.entity.Platform;
import com.whoiszxl.mapper.PlatformMapper;
import com.whoiszxl.service.PlatformService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信平台表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements PlatformService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Platform getPlatformByCache(String accessKeyId) {
        String platformJson = (String) redisUtils.hGet(RedisKeyPrefixConstants.SMS_PLATFORM_LIST, accessKeyId);
        if(StringUtils.isBlank(platformJson)) {
            Platform platform = this.getOne(Wrappers.<Platform>lambdaQuery().eq(Platform::getAccessKeyId, accessKeyId));
            redisUtils.hPut(RedisKeyPrefixConstants.SMS_PLATFORM_LIST, accessKeyId, JsonUtil.toJson(platform));
            return platform;
        }
        return JsonUtil.fromJson(platformJson, Platform.class);
    }
}
