package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采购订单表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_purchase_order")
@ApiModel(value="PurchaseOrder对象", description="采购订单表")
public class PurchaseOrder extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;

    @ApiModelProperty(value = "预计到货时间")
    private Date expectArrivalTime;

    @ApiModelProperty(value = "实际到货时间")
    private Date arrivalTime;

    @ApiModelProperty(value = "采购联系人")
    private String purchaseContactor;

    @ApiModelProperty(value = "采购人联系电话")
    private String purchaseContactPhoneNumber;

    @ApiModelProperty(value = "采购人联系邮箱")
    private String purchaseContactEmail;

    @ApiModelProperty(value = "说明备注")
    private String comment;

    @ApiModelProperty(value = "采购员")
    private String purchaser;

    @ApiModelProperty(value = "采购单状态，1：编辑中，2：待审核，3：已审核，4：待入库，5：待结算，6：已完成")
    private Integer purchaseOrderStatus;

    @ApiModelProperty(value = "乐观锁")
    private Long version;

    @ApiModelProperty(value = "逻辑删除 1: 已删除， 0: 未删除")
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}
