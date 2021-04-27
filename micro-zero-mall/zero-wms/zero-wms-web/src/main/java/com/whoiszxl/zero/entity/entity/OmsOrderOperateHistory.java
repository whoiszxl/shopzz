package com.whoiszxl.zero.entity.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 订单操作历史记录表(OmsOrderOperateHistory)实体类
 *
 * @author makejava
 * @since 2021-04-27 11:43:12
 */
public class OmsOrderOperateHistory implements Serializable {
    private static final long serialVersionUID = 740868868519511729L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 订单ID
    */
    private Long orderId;
    /**
    * 操作人类型[1:用户；2:系统；3:后台管理员]
    */
    private Object operateByType;
    /**
    * 操作人名称
    */
    private String operateByName;
    /**
    * 操作类型，1：创建订单，2：手动取消订单，3：自动取消订单，4：支付订单，5：手动确认收货，6：自动确认收货，7：商品发货，8：申请退货，9：退货审核不通过，10：退货审核通过，11：寄送退货商品，12：确认收到退货，13：退货已入库，14：完成退款
    */
    private Object operateType;
    /**
    * 操作备注
    */
    private String operateNote;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Object getOperateByType() {
        return operateByType;
    }

    public void setOperateByType(Object operateByType) {
        this.operateByType = operateByType;
    }

    public String getOperateByName() {
        return operateByName;
    }

    public void setOperateByName(String operateByName) {
        this.operateByName = operateByName;
    }

    public Object getOperateType() {
        return operateType;
    }

    public void setOperateType(Object operateType) {
        this.operateType = operateType;
    }

    public String getOperateNote() {
        return operateNote;
    }

    public void setOperateNote(String operateNote) {
        this.operateNote = operateNote;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}