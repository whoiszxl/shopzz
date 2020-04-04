package com.whoiszxl.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("mall_return_order")
public class ReturnOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务单号
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系人手机
     */
    private String linkmanMobile;

    /**
     * 类型
     */
    private String type;

    /**
     * 退款金额
     */
    private Integer returnMoney;

    /**
     * 是否退运费
     */
    private String isReturnFreight;

    /**
     * 申请状态
     */
    private String status;

    /**
     * 处理时间
     */
    private LocalDateTime disposeTime;

    /**
     * 退货退款原因
     */
    private Integer returnCause;

    /**
     * 凭证图片
     */
    private String evidence;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 处理备注
     */
    private String remark;

    /**
     * 管理员id
     */
    private Integer adminId;


}
