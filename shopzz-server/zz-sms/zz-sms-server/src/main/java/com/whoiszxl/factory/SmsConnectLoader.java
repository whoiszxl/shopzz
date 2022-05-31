package com.whoiszxl.factory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.DistributedLock;
import com.whoiszxl.DistributedLockFactory;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.entity.Channel;
import com.whoiszxl.entity.ServerTopic;
import com.whoiszxl.entity.SmsChannelDTO;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.service.ChannelService;
import com.whoiszxl.service.impl.SignatureServiceImpl;
import com.whoiszxl.service.impl.TemplateServiceImpl;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisUtils;
import com.whoiszxl.utils.SpringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * SMS 通道加载器
 *
 * @author whoiszxl
 * @date 2022/5/28
 */
@Slf4j
@Component
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class SmsConnectLoader implements CommandLineRunner {

    private static final List<Object> CONNECT_LIST = new ArrayList<>();
    private static List<Channel> FUTURE_CHANNEL_LIST;
    private static String BUILD_NEW_CONNECT_TOKEN = null;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private SpringUtil springUtil;

    @Override
    public void run(String... args) {
        initConnect();
    }

    /**
     * 初始化每个通道
     */
    @SneakyThrows
    public void initConnect() {
        List<Channel> channelList = channelService.list(Wrappers.<Channel>lambdaQuery()
                .eq(Channel::getChannelType, 1)
                .eq(Channel::getStatus, StatusEnum.OPEN.getCode()).orderByAsc(Channel::getLevel));

        log.info("SmsConnectLoader|当前的可用通道|{}", channelList);

        List<Object> beanList = new ArrayList<>();
        for (Channel channel : channelList) {
            try {
                //构建DTO
                SmsChannelDTO smsChannelDTO = buildDTO(channel);

                //反射创建,拼出全类名
                String className = "com.whoiszxl.sms.impl." + smsChannelDTO.getPlatform() + "SmsService";
                Class<?> aClass = Class.forName(className);

                //获取构造方法创建bean对象
                Constructor<?> constructor = aClass.getConstructor(SmsChannelDTO.class);
                Object beanService = constructor.newInstance(smsChannelDTO);
                
                //给反射创建的服务的成员变量赋值
                Field signatureServiceField = aClass.getSuperclass().getDeclaredField("signatureService");
                Field templateServiceField = aClass.getSuperclass().getDeclaredField("templateService");
                signatureServiceField.setAccessible(true);
                templateServiceField.setAccessible(true);

                SignatureServiceImpl signatureService = springUtil.getBean(SignatureServiceImpl.class);
                TemplateServiceImpl templateService = springUtil.getBean(TemplateServiceImpl.class);
                signatureServiceField.set(beanService, signatureService);
                templateServiceField.set(beanService, templateService);

                beanList.add(beanService);
            }catch (Exception e) {
                log.error("SmsConnectLoader|初始化短信通道发生异常|", e);
            }
        }


        if(!CONNECT_LIST.isEmpty()) {
            CONNECT_LIST.clear();
        }
        CONNECT_LIST.addAll(beanList);

    }


    public <T> T getConnectByLevel(Integer level) {
        return (T) CONNECT_LIST.get(level - 1);
    }

    public boolean checkConnectLevel(Integer level) {
        return CONNECT_LIST.size() <= level;
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
            smsChannelDTO.setOtherConfig(JsonUtil.fromJson(channel.getOtherConfig(), LinkedHashMap.class));
        }
        return smsChannelDTO;
    }

    public void changeNewConnectMessage() {
        redisUtils.convertAndSend(
                RedisKeyPrefixConstants.SMS_TOPIC_SERVER,
                ServerTopic.builder()
                        .option(ServerTopic.USE_NEW_CONNECT)
                        .value(ServerRegister.SERVER_ID)
                        .build().toString());
    }

    public void buildNewConnect() {
        DistributedLock distributedLock = distributedLockFactory.getDistributedLock(RedisKeyPrefixConstants.SMS_LOCK_BUILD_NEW_CONNECT);

        try{
            boolean lockFlag = distributedLock.tryLock(5, 1, TimeUnit.HOURS);
            if(lockFlag) {
                List<Channel> channelList = channelService.listForNewConnect();
                FUTURE_CHANNEL_LIST = channelList;
                redisUtils.set(RedisKeyPrefixConstants.SMS_NEW_CONNECT_SERVER, ServerRegister.SERVER_ID);
            }
        }catch (Exception e) {
            log.error("SmsConnectLoader|构建新通道连接发生异常|", e);
        }finally {
            distributedLock.unlock();
        }
    }

    /**
     * 通道调整
     * 发布订阅消息，通知其他服务：初始化新通道
     */
    public void changeNewConnect() {
        // 初始化通道
        String newConnectServer = redisUtils.get(RedisKeyPrefixConstants.SMS_NEW_CONNECT_SERVER);

        if (StringUtils.isNotBlank(newConnectServer) && ServerRegister.SERVER_ID.equals(newConnectServer) &&
                !CollectionUtils.isEmpty(FUTURE_CHANNEL_LIST)) {
            // 配置列表不为空则执行数据库操作 并清空缓存

            channelService.updateBatchById(FUTURE_CHANNEL_LIST);
            FUTURE_CHANNEL_LIST.clear();

            redisUtils.convertAndSend(RedisKeyPrefixConstants.SMS_TOPIC_SERVER,
                    ServerTopic.builder()
                            .option(ServerTopic.INIT_CONNECT)
                            .value(ServerRegister.SERVER_ID)
                            .build().toString());
        }
    }
}
