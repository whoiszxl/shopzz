package com.whoiszxl.cqrs.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 秒杀下单命令
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
@Data
@ApiModel("秒杀下单命令")
public class SeckillOrderSubmitCommand {

    @ApiModelProperty("秒杀活动ID")
    private Long seckillId;

    @ApiModelProperty("秒杀item id")
    private Long seckillItemId;

    @ApiModelProperty("秒杀数量")
    private Integer quantity;
}
