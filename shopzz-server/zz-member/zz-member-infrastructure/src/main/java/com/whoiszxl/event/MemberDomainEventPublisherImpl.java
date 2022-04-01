package com.whoiszxl.event;

import com.whoiszxl.constants.RocketMQConstant;
import com.whoiszxl.events.MemberDomainEventPublisher;
import com.whoiszxl.events.MemberLoginSuccessEvent;
import com.whoiszxl.mq.RocketMQProducer;
import com.whoiszxl.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 领域事件发布服务实现
 *
 * @author whoiszxl
 * @date 2022/1/24
 */
@Component
public class MemberDomainEventPublisherImpl implements MemberDomainEventPublisher {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Override
    public void publishMemberLoginSuccessEvent(MemberLoginSuccessEvent memberLoginSuccessEvent) {
        String message = JsonUtil.toJson(memberLoginSuccessEvent);
        rocketMQProducer.sendMessage(RocketMQConstant.MEMBER_LOGIN_TOPIC, message, "用户登录成功的领域事件");
    }

}
