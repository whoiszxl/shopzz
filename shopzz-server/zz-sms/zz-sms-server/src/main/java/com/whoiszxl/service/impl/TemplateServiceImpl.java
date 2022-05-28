package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.entity.Template;
import com.whoiszxl.mapper.TemplateMapper;
import com.whoiszxl.service.TemplateService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 短信模板表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Template getCacheByTemplateCode(String templateCode) {
        String templateJson = redisUtils.get(RedisKeyPrefixConstants.SMS_TEMPLATE + templateCode);
        if(StringUtils.isBlank(templateJson)) {
            Template template = this.getOne(Wrappers.<Template>lambdaQuery().eq(Template::getCode, templateCode));
            redisUtils.setEx(RedisKeyPrefixConstants.SMS_TEMPLATE + templateCode, JsonUtil.toJson(template), 1, TimeUnit.MINUTES);
            return template;
        }

        return JsonUtil.fromJson(templateJson, Template.class);
    }
}
