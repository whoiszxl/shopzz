package com.whoiszxl.cqrs.command;

import lombok.Data;

@Data
public class CheckCartItemCommand {

    private Integer isChecked;

    private Long skuId;
}
