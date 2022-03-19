package com.whoiszxl.dto;

import com.whoiszxl.bean.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 采购供应商表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchaseSupplier对象", description="采购供应商表")
public class PurchaseSupplierDTO extends AbstractObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "联系人")
    private String contactor;

    @ApiModelProperty(value = "联系电话")
    private String contactPhoneNumber;

    @ApiModelProperty(value = "账期，1：周结算，2：月结算，3：季度结算")
    private Integer accountPeriod;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行账户")
    private String bankAccount;

    @ApiModelProperty(value = "开户人")
    private String accountHolder;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceTitle;

    @ApiModelProperty(value = "纳税人识别号")
    private String taxpayerId;

    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    @ApiModelProperty(value = "说明备注")
    private String supplierComment;

    @ApiModelProperty(value = "企业资质")
    private String enterpriseQualification;

    @ApiModelProperty(value = "合作合同")
    private String cooperateContract;

    @ApiModelProperty(value = "协议价合同")
    private String priceContract;

    @ApiModelProperty(value = "采购合同")
    private String purchaseContract;


}
