package com.whoiszxl.taowu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentModuleEnum {
    VIDEO(1),
    PRODUCT(2);

    private final int value;

    public static boolean isValid(int value) {
        for (CommentModuleEnum type : CommentModuleEnum.values()) {
            if (type.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}