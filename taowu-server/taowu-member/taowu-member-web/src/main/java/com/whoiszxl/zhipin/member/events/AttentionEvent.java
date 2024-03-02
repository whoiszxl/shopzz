package com.whoiszxl.zhipin.member.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 关注事件
 * @author whoiszxl
 */
public class AttentionEvent extends ApplicationEvent {

    @Getter
    private Long attentionMemberId;

    public AttentionEvent(Object source, Long attentionMemberId) {
        super(source);
        this.attentionMemberId = attentionMemberId;
    }
}
