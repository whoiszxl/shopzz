package com.whoiszxl.cqrs.response;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * <p>
 * 采购供应商表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-18
 */
@Data
@ApiModel(value = "PurchaseSupplier对象", description = "采购供应商表")
public class PurchaseSupplierResponse implements Serializable {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("公司地址")
    private String companyAddress;

    @ApiModelProperty("联系人")
    private String contactor;

    @ApiModelProperty("联系电话")
    private String contactPhoneNumber;

    @ApiModelProperty("账期, 1:周结算, 2:月结算, 3:季度结算")
    private Integer accountPeriod;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("银行账户")
    private String bankAccount;

    @ApiModelProperty("开户人")
    private String accountHolder;

    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("纳税人识别号")
    private String taxpayerId;

    @ApiModelProperty("经营范围")
    private String businessScope;

    @ApiModelProperty("说明备注")
    private String supplierComment;

    @ApiModelProperty("企业资质")
    private String enterpriseQualification;

    @ApiModelProperty("合作合同")
    private Blob cooperateContract;

    @ApiModelProperty("协议价合同")
    private Blob priceContract;

    @ApiModelProperty("采购合同")
    private Blob purchaseContract;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
