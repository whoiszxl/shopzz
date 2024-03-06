package com.whoiszxl.taowu.cqrs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀item表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Data
@Schema(description = "秒杀item表")
public class SeckillItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    private Long id;

    @Schema(description = "关联秒杀表的主键ID")
    private Long seckillId;

    @Schema(description = "秒杀SKU名称")
    private String skuName;

    @Schema(description = "秒杀SKU描述")
    private String skuDescs;

    @Schema(description = "sku图片")
    private String skuImg;

    @Schema(description = "spu名称")
    private String spuName;

    @Schema(description = "秒杀开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "秒杀结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Schema(description = "秒杀初始库存")
    private Integer initStockQuantity;

    @Schema(description = "秒杀可用库存")
    private Integer availableStockQuantity;

    @Schema(description = "秒杀库存是否预热: 0-未预热 1-已预热")
    private Integer warmUpStatus;

    @Schema(description = "SKU价格")
    private BigDecimal skuPrice;

    @Schema(description = "秒杀价格")
    private BigDecimal seckillPrice;

    @Schema(description = "秒杀SKU是否启动: 0-未启动 1-已启动")
    private Integer status;


}
