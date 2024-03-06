package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 秒杀订单结果异步获取命令
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
@Data
@Schema(description = "秒杀订单结果异步获取命令")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeckillOrderResultCommand {

    @Schema(description = "秒杀item id")
    private Long seckillItemId;

    @Schema(description = "异步下单任务ID taskKey")
    private String taskId;
}
