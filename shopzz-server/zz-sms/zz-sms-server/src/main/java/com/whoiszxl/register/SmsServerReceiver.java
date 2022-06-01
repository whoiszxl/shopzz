package com.whoiszxl.register;

import com.whoiszxl.entity.ServerTopic;
import com.whoiszxl.factory.SmsConnectLoader;
import com.whoiszxl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * SMS服务接收者
 * 监听Redis的发布订阅消息
 *
 * @author whoiszxl
 * @date 2022/5/30
 */
@Slf4j
@Component
public class SmsServerReceiver implements MessageListener {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SmsConnectLoader smsConnectLoader;

    /**
     *
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        ServerTopic serverTopic = ServerTopic.load(json);

        switch (serverTopic.getOption()) {
            case ServerTopic.INIT_CONNECT:
                smsConnectLoader.initConnect();
                break;
            case ServerTopic.USE_NEW_CONNECT:
                smsConnectLoader.changeNewConnect();
                break;
            default:
                break;
        }
    }
}
