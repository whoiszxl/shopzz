package com.whoiszxl.factory;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.entity.Channel;
import com.whoiszxl.entity.SmsChannelDTO;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.service.ChannelService;
import com.whoiszxl.utils.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SMS 通道加载器
 *
 * @author whoiszxl
 * @date 2022/5/28
 */
@Slf4j
@Component
public class SmsConnectLoader implements CommandLineRunner {

    @Autowired
    private ChannelService channelService;

    @Override
    public void run(String... args) throws Exception {
        initConnect();
    }

    /**
     * 初始化每个通道
     */
    @SneakyThrows
    public void initConnect() {
        List<Channel> channelList = channelService.list(Wrappers.<Channel>lambdaQuery()
                .eq(Channel::getChannelType, 1)
                .eq(Channel::getStatus, StatusEnum.OPEN.getCode()));

        log.info("SmsConnectLoader|当前的可用通道|{}", channelList);

        List beanList = new ArrayList();
        for (Channel channel : channelList) {
            try {
                //构建DTO
                SmsChannelDTO smsChannelDTO = buildDTO(channel);

                //反射创建,拼出全类名
                String className = "com.whoiszxl.sms." + smsChannelDTO.getPlatform() + "SmsService";
                Class<?> aClass = Class.forName(className);

                //获取构造方法创建bean对象
                Constructor<?> constructor = aClass.getConstructor(SmsChannelDTO.class);
                Object beanService = constructor.newInstance(smsChannelDTO);
                
            }
        }

    }

    private SmsChannelDTO buildDTO(Channel channel) {
        SmsChannelDTO smsChannelDTO = new SmsChannelDTO();
        smsChannelDTO.setId(channel.getId());
        smsChannelDTO.setName(channel.getName().trim());
        smsChannelDTO.setPlatform(channel.getPlatform().trim());
        smsChannelDTO.setDomain(channel.getDomain().trim());
        smsChannelDTO.setAccessKeyId(channel.getAccessKeyId().trim());
        smsChannelDTO.setAccessKeySecret(channel.getAccessKeySecret().trim());
        smsChannelDTO.setLevel(channel.getLevel());
        smsChannelDTO.setChannelType(channel.getChannelType());
        smsChannelDTO.setStatus(channel.getStatus());

        if(StringUtils.isNotBlank(channel.getOtherConfig())) {
            LinkedHashMap linkedHashMap = JsonUtil.fromJson(channel.getOtherConfig(), LinkedHashMap.class);
            smsChannelDTO.setOtherConfig(linkedHashMap);
        }
        return smsChannelDTO;
    }
}
