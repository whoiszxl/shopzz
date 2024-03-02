package com.whoiszxl.zhipin.member.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 关注计数监听器
 * @author whoiszxl
 */
@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class AttentionCounterListener implements ApplicationListener<AttentionEvent> {

    @Override
    public void onApplicationEvent(AttentionEvent event) {
        log.info("AttentionEvent|关注事件统计计数|{}", event.getAttentionMemberId());

    }
}