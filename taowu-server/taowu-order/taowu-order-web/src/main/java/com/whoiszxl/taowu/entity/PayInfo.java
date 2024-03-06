package com.whoiszxl.taowu.entity;

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
 * 第三方支付信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@TableName("oms_pay_info")
@Schema(description = "第三方支付信息表")
public class PayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "订单总支付金额")
    private BigDecimal totalAmount;

    @Schema(description = "交易渠道，1：支付宝，2：微信")
    private Integer tradeChannel;

    @Schema(description = "交易流水号，第三方支付平台生成")
    private String tradeNo;

    @Schema(description = "支付状态，1：待支付，2：支付成功，3：支付失败，4：交易关闭；5：退款")
    private Integer status;

    @Schema(description = "完成第三方支付的时间")
    private LocalDateTime complatedTime;

    @Schema(description = "乐观锁")
    @Version
    private Long version;

    @Schema(description = "逻辑删除 1: 已删除， 0: 未删除")
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
