package com.whoiszxl.zero.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum MemberStatusEnum {

    MEMBER_VAILD("有效", 1),
    MEMBER_INVAILD("无效", 0);

    private String msg;
    private Integer status;
}
