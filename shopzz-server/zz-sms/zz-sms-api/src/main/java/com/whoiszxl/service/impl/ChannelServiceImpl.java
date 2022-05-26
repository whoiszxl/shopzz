package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Channel;
import com.whoiszxl.mapper.ChannelMapper;
import com.whoiszxl.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 短信通道表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public List<Channel> listByTemplateAndSignature(Long templateId, Long signatureId) {
        return channelMapper.listByTemplateAndSignature(templateId, signatureId);
    }
}
