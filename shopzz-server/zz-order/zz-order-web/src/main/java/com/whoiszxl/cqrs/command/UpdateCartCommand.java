package com.whoiszxl.cqrs.command;

import lombok.Data;

@Data
public class UpdateCartCommand {

    private Long skuId;

    private Integer quantity;
}
