package com.whoiszxl.cqrs.response;

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
 * 秒杀订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Getter
@Setter
@ApiModel(value = "SeckillOrder对象", description = "秒杀订单表")
public class SeckillOrderResponse implements Serializable {

    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("会员ID")
    private Long memberId;

    @ApiModelProperty("关联秒杀表的主键ID")
    private Long seckillId;

    @ApiModelProperty("关联秒杀item表的主键ID")
    private Long seckillItemId;

    @ApiModelProperty("秒杀SKU名称")
    private String skuName;

    @ApiModelProperty("SKU价格")
    private BigDecimal skuPrice;

    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    @ApiModelProperty("秒杀数量")
    private Integer buyQuantity;

    @ApiModelProperty("最终订单应付总额")
    private BigDecimal finalPayAmount;

    @ApiModelProperty("秒杀订单状态: 0-未支付 1-已支付")
    private Integer status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;



}
