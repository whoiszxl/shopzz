package com.whoiszxl.factory;

import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 服务注册器，将服务注册到Redis，定时上报和检查
 */
@Component
@Slf4j
@Order(value = 100)
public class ServerRegister implements CommandLineRunner {

    //当前服务实例的唯一标识
    public static String SERVER_ID = null;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run(String... args) {
        SERVER_ID = UUID.randomUUID().toString();
        log.info("ServerRegister|为当前服务生成了一个唯一标识|{}", SERVER_ID);
        redisUtils.hPut(RedisKeyPrefixConstants.SMS_SERVER_ID_HASH, SERVER_ID, String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 一分钟上报一次服务
     */
    @Scheduled(cron = "1 0/1 * * * ?")
    public void serverReport() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        log.info("ServerRegister|定时上报当前服务状态|{},{}",SERVER_ID, timestamp);
        redisUtils.hPut(RedisKeyPrefixConstants.SMS_SERVER_ID_HASH, SERVER_ID, timestamp);
    }

    /**
     * 两分钟检查一次，剔除超过3分钟的服务
     */
    @Scheduled(cron = "2 0/2 * * * ?")
    public void checkServer() {
        log.info("ServerRegister|定时检查服务状态是否可用|{}", SERVER_ID);
        Map<Object, Object> map = redisUtils.hGetAll(RedisKeyPrefixConstants.SMS_SERVER_ID_HASH);

        long current = System.currentTimeMillis();
        List<Object> removeKeys = new ArrayList<>();
        map.forEach((key,value) -> {
            long parseLong = Long.parseLong(value.toString());
            if(current - parseLong > (1000 * 60 * 3)){
                //超过5分钟没有上报则添加到移除列表里
                removeKeys.add(key);
            }
        });

        //清理服务实例
        removeKeys.forEach(key ->{
            log.info("ServerRegister|清除缓存中的服务实例|{}", key);
            redisUtils.hDel(RedisKeyPrefixConstants.SMS_SERVER_ID_HASH, key.toString());
        });
    }
}

