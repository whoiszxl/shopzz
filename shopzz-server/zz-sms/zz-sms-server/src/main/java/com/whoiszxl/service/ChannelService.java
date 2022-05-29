package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.entity.Channel;

import java.util.List;

/**
 * <p>
 * 短信通道表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
public interface ChannelService extends IService<Channel> {

    List<Channel> listByTemplateAndSignature(Long templateId, Long signature);

    List<Channel> listForNewConnect();
}
