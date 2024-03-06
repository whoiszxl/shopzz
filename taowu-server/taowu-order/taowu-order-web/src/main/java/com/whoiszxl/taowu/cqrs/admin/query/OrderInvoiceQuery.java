package com.whoiszxl.taowu.cqrs.admin.query;

import com.whoiszxl.taowu.common.annotation.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单发票表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@Schema(description = "订单发票查询条件")
public class OrderInvoiceQuery implements Serializable {

    @Query
    @Schema(description = "订单ID")
    private Long orderId;

    @Query
    @Schema(description = "发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer invoiceType;

    @Query
    @Schema(description = "收票人电话")
    private String invoiceReceiverPhone;

    @Query
    @Schema(description = "公司名称[增值税]")
    private String companyName;

}
