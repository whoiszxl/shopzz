package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.entity.ChannelTemplate;
import com.whoiszxl.entity.Template;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.mapper.TemplateMapper;
import com.whoiszxl.service.ChannelTemplateService;
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

    @Autowired
    private ChannelTemplateService channelTemplateService;

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

    @Override
    public String getChannelCodeByTemplateCode(Long channelId, String templateCode) {
        //1. 先查询到模板实体
        Template template = this.getOne(Wrappers.<Template>lambdaQuery()
                .eq(Template::getCode, templateCode)
                .eq(Template::getStatus, StatusEnum.OPEN.getCode()));

        //2. 拿到短信通道模板实体
        ChannelTemplate channelTemplate = channelTemplateService.getOne(Wrappers.<ChannelTemplate>lambdaQuery()
                .eq(ChannelTemplate::getChannelId, channelId)
                .eq(ChannelTemplate::getTemplateId, template.getId()));

        return channelTemplate != null ? channelTemplate.getChannelTemplateCode() : "";
    }
}
