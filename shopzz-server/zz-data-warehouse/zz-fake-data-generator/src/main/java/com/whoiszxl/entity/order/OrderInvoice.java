package com.whoiszxl.entity.order;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("oms_order_invoice")
@ApiModel(value = "OrderInvoice对象", description = "订单发票表")
public class OrderInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer invoiceType;

    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("发票内容")
    private String invoiceContent;

    @ApiModelProperty("发票税号")
    private String invoiceTaxNo;

    @ApiModelProperty("收票人电话")
    private String invoiceReceiverPhone;

    @ApiModelProperty("收票人邮箱")
    private String invoiceReceiverEmail;

    @ApiModelProperty("收票人收货地址")
    private String invoiceReceiverAddress;

    @ApiModelProperty("开票金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("公司名称[增值税]")
    private String companyName;

    @ApiModelProperty("公司地址[增值税]")
    private String companyAddress;

    @ApiModelProperty("联系电话[增值税]")
    private String telphone;

    @ApiModelProperty("开户银行[增值税]")
    private String bankName;

    @ApiModelProperty("银行账号[增值税]")
    private String bankAccount;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除， 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
