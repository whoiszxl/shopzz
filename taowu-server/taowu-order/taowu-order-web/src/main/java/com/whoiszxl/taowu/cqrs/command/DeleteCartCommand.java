package com.whoiszxl.taowu.cqrs.command;

import lombok.Data;

import java.util.List;

@Data
public class DeleteCartCommand {

    private List<Long> skuIdList;
}
