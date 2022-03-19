package com.whoiszxl.cqrs.response;

import com.whoiszxl.cqrs.vo.PurchaseOrderItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采购订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Data
@ApiModel(value = "PurchaseOrderResponse对象", description = "PurchaseOrderResponse")
public class PurchaseOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("供应商ID")
    private Long supplierId;

    @ApiModelProperty("预计到货时间")
    private LocalDateTime expectArrivalTime;

    @ApiModelProperty("实际到货时间")
    private LocalDateTime arrivalTime;

    @ApiModelProperty("采购联系人")
    private String purchaseContactor;

    @ApiModelProperty("采购人联系电话")
    private String purchaseContactPhoneNumber;

    @ApiModelProperty("采购人联系邮箱")
    private String purchaseContactEmail;

    @ApiModelProperty("说明备注")
    private String comment;

    @ApiModelProperty("采购员")
    private String purchaser;

    @ApiModelProperty("采购单状态, 1:编辑中, 2:待审核, 3:已审核, 4:待入库, 5:审核待入库, 6:已入库, 7:待结算, 8:已完成")
    private Integer purchaseOrderStatus;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "采购订单详情")
    private List<PurchaseOrderItemVO> items;

}
