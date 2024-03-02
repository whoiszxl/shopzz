package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author whoiszxl
 */
@Getter
@AllArgsConstructor
public enum CounterVideoTypeEnum {

    LIKE_COUNT(0, "获赞数"),
    COMMENT_COUNT(1, "评论数"),
    SHARE_COUNT(2, "分享数"),
    WATCH_COUNT(3, "浏览数");

    private final int type;
    private final String description;

    public static String getNameByCode(int code) {
        for (CounterVideoTypeEnum type : CounterVideoTypeEnum.values()) {
            if (type.type == code) {
                return type.name().toLowerCase();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int code = 2;
        String description = CounterVideoTypeEnum.getNameByCode(code);
        System.out.println("Description for code " + code + ": " + description);
    }
}
