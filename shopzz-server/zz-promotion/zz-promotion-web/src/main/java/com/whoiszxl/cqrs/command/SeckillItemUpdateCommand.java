package com.whoiszxl.cqrs.command;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("spms_seckill_item")
@ApiModel(value = "SeckillItem对象", description = "秒杀item表")
public class SeckillItemUpdateCommand implements Serializable {


    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("关联秒杀表的主键ID")
    private Long seckillId;

    @ApiModelProperty("秒杀SKU名称")
    private String skuName;

    @ApiModelProperty("秒杀SKU描述")
    private String skuDescs;

    @ApiModelProperty("秒杀开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty("秒杀初始库存")
    private Integer initStockQuantity;

    @ApiModelProperty("秒杀可用库存")
    private Integer availableStockQuantity;

    @ApiModelProperty("秒杀库存是否预热: 0-未预热 1-已预热")
    private Integer warmUpStatus;

    @ApiModelProperty("SKU价格")
    private BigDecimal skuPrice;

    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    @ApiModelProperty("秒杀SKU是否启动: 0-未启动 1-已启动")
    private Integer status;

}
