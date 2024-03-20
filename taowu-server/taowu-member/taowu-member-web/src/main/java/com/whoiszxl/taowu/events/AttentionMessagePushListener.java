package com.whoiszxl.taowu.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 关注消息推送监听
 * @author whoiszxl
 */
@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class AttentionMessagePushListener implements ApplicationListener<AttentionEvent> {

    @Override
    public void onApplicationEvent(AttentionEvent event) {
        log.info("AttentionEvent|关注事件消息推送|{}", event.getAttentionMemberId());
    }
}