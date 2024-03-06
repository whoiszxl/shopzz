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
 * 订单退货表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-09
 */
@Data
@TableName("oms_order_return_apply")
@Schema(description = "订单退货表")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单明细项ID")
    private Long orderItemId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单中SKU的ID")
    private Long skuId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "运费")
    private BigDecimal freight;

    @Schema(description = "退货数量")
    private Integer returnCount;

    @Schema(description = "退货原因，1：质量不好，2：商品不满意，3：买错了，4：无理由退货")
    private Integer returnReason;

    @Schema(description = "退货备注")
    private String returnComment;

    @Schema(description = "退货图片备注，逗号分割")
    private String returnPic;

    @Schema(description = "退货记录状态，1：待审核，2：审核不通过，3：审核通过")
    private Integer returnApplyStatus;

    @Schema(description = "退货快递单号")
    private String returnLogisticCode;

    @Schema(description = "收货人")
    private String returnReceiveName;

    @Schema(description = "收货备注")
    private String returnReceiveNote;

    @Schema(description = "收货电话")
    private String returnReceivePhone;

    @Schema(description = "公司收货地址")
    private String returnCompanyAddress;

    @Schema(description = "收货时间")
    private LocalDateTime returnReceiveTime;

    @Schema(description = "处理备注")
    private String handleNote;

    @Schema(description = "处理人员")
    private String handleBy;

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
