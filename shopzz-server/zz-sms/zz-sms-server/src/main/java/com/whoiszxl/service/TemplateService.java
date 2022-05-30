package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Template;

/**
 * <p>
 * 短信模板表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
public interface TemplateService extends IService<Template> {

    /**
     * 从缓存获取短信模板配置 - 走缓存
     * @param templateCode
     * @return
     */
    Template getCacheByTemplateCode(String templateCode);

    String getChannelCodeByTemplateCode(Long channelId, String templateCode);
}
