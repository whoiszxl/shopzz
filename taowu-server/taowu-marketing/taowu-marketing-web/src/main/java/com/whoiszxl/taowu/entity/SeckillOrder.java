package com.whoiszxl.taowu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
@TableName("spms_seckill_order")
@Schema(description = "秒杀订单表")
public class SeckillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "自增主键ID")
    @TableId(value = "id")
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

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "更新者")
    private String updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
