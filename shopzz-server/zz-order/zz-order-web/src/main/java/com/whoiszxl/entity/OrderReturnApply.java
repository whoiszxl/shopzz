package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单退货表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Getter
@Setter
@TableName("oms_order_return_apply")
@ApiModel(value = "OrderReturnApply对象", description = "订单退货表")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      private Long id;

    @ApiModelProperty("订单ID")
    private Long orderId;

    @ApiModelProperty("订单明细项ID")
    private Long orderItemId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单中SKU的ID")
    private Long skuId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("运费")
    private BigDecimal freight;

    @ApiModelProperty("退货数量")
    private Integer returnCount;

    @ApiModelProperty("退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货")
    private Integer returnReason;

    @ApiModelProperty("退货备注")
    private String returnComment;

    @ApiModelProperty("退货图片备注，逗号分割")
    private String returnPic;

    @ApiModelProperty("退货记录状态，1：待审核，2：审核不通过，3：审核通过")
    private Integer returnApplyStatus;

    @ApiModelProperty("退货快递单号")
    private String returnLogisticCode;

    @ApiModelProperty("收货人")
    private String returnReceiveName;

    @ApiModelProperty("收货备注")
    private String returnReceiveNote;

    @ApiModelProperty("收货电话")
    private String returnReceivePhone;

    @ApiModelProperty("公司收货地址")
    private String returnCompanyAddress;

    @ApiModelProperty("收货时间")
    private LocalDateTime returnReceiveTime;

    @ApiModelProperty("处理备注")
    private String handleNote;

    @ApiModelProperty("处理人员")
    private String handleBy;

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
