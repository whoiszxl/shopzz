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
 * 订单发票表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@TableName("oms_order_invoice")
@Schema(description = "订单发票表")
public class OrderInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer invoiceType;

    @Schema(description = "发票抬头")
    private String invoiceTitle;

    @Schema(description = "发票内容")
    private String invoiceContent;

    @Schema(description = "发票税号")
    private String invoiceTaxNo;

    @Schema(description = "收票人电话")
    private String invoiceReceiverPhone;

    @Schema(description = "收票人邮箱")
    private String invoiceReceiverEmail;

    @Schema(description = "收票人收货地址")
    private String invoiceReceiverAddress;

    @Schema(description = "开票金额")
    private BigDecimal totalAmount;

    @Schema(description = "公司名称[增值税]")
    private String companyName;

    @Schema(description = "公司地址[增值税]")
    private String companyAddress;

    @Schema(description = "联系电话[增值税]")
    private String telphone;

    @Schema(description = "开户银行[增值税]")
    private String bankName;

    @Schema(description = "银行账号[增值税]")
    private String bankAccount;

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
