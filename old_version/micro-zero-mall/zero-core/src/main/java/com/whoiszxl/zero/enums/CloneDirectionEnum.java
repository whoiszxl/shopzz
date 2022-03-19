package com.whoiszxl.zero.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 克隆方向枚举类
 *
 * @author whoiszxl
 * @date 2021/3/17
 */
@Getter
@AllArgsConstructor
public enum CloneDirectionEnum {

    POSITIVE(1, "正向克隆：从VO->DTO，DTO->DO"),
    REVERSE(2, "反向克隆：从DO->DTO，DTO->VO");

    private Integer code;
    private String msg;
}
