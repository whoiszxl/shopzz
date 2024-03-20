package com.whoiszxl.taowu.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 关注事件
 * @author whoiszxl
 */
public class AttentionEvent extends ApplicationEvent {

    @Getter
    private final Long attentionMemberId;

    @Getter
    private final Long currentMemberId;

    public AttentionEvent(Object source, Long currentMemberId, Long attentionMemberId) {
        super(source);
        this.currentMemberId = currentMemberId;
        this.attentionMemberId = attentionMemberId;
    }
}
