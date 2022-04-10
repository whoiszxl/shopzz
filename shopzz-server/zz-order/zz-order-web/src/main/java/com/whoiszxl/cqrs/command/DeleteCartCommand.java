package com.whoiszxl.cqrs.command;

import lombok.Data;

import java.util.List;

@Data
public class DeleteCartCommand {

    private List<Long> skuIdList;
}
