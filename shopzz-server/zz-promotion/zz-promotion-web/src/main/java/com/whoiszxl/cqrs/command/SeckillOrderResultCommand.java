package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 秒杀订单结果异步获取命令
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
@Data
@ApiModel("秒杀订单结果异步获取命令")
public class SeckillOrderResultCommand {

    @ApiModelProperty("秒杀item id")
    private Long seckillItemId;

    @ApiModelProperty("异步下单任务ID")
    private String taskId;
}
