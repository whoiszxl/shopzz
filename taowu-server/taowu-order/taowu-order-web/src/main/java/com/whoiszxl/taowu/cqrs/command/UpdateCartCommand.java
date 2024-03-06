package com.whoiszxl.taowu.cqrs.command;

import lombok.Data;

@Data
public class UpdateCartCommand {

    private Long skuId;

    private Integer quantity;
}
