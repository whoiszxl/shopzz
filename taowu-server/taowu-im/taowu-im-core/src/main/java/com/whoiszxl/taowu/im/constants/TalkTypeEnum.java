package com.whoiszxl.taowu.im.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对话类型枚举
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum TalkTypeEnum {

    /**
     * 私聊
     */
    PRIVATE_CHAT(1, "私聊"),

    /**
     * 群聊
     */
    GROUP_CHAT(2, "群聊"),

    /**
     * CHAT GPT
     */
    GPT_CHAT(3, "CHAT GPT"),

    /**
     * 机器人
     */
    ROBOT_CHAT(4, "机器人"),
    ;

    private final Integer code;
    private final String desc;
}