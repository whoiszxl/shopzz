package com.whoiszxl.cqrs.command;

import com.whoiszxl.cqrs.vo.PurchaseOrderItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采购订单保存命令实体
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Data
@ApiModel(value = "采购订单保存命令实体", description = "采购订单保存命令实体")
public class PurchaseOrderSaveCommand implements Serializable {


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


    @ApiModelProperty(value = "采购订单详情")
    private List<PurchaseOrderItemVO> items;

}
