package com.whoiszxl.taowu.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@Schema(description = "秒杀订单表")
public class SeckillOrderResponse implements Serializable {

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "关联秒杀表的主键ID")
    private Long seckillId;

    @Schema(description = "关联秒杀item表的主键ID")
    private Long seckillItemId;

    @Schema(description = "秒杀SKU名称")
    private String skuName;

    @Schema(description = "SKU价格")
    private BigDecimal skuPrice;

    @Schema(description = "秒杀价格")
    private BigDecimal seckillPrice;

    @Schema(description = "秒杀数量")
    private Integer buyQuantity;

    @Schema(description = "最终订单应付总额")
    private BigDecimal finalPayAmount;

    @Schema(description = "秒杀订单状态: 0-未支付 1-已支付")
    private Integer status;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;



}
