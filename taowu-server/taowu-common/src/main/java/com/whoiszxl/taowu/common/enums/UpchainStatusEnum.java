package com.whoiszxl.taowu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpchainStatusEnum {

    SUCCESS("上链并确认成功", 1),
    WAITING_CONFIRM("等待确认中", 2),
    NOT_UPCHAIN("未上链", 3);

    private String msg;
    private Integer code;
}
