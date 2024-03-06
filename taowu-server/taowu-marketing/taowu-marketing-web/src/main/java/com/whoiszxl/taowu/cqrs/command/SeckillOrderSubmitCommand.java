package com.whoiszxl.taowu.cqrs.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 秒杀下单命令
 *
 * @author whoiszxl
 * @date 2022/4/20
 */
@Data
@Schema(description = "秒杀下单命令")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeckillOrderSubmitCommand {

    @Schema(description = "秒杀活动ID")
    private Long seckillId;

    @Schema(description = "秒杀item id")
    private Long seckillItemId;

    @Schema(description = "秒杀数量")
    private Integer quantity = 1;
}
