package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 优惠券表(PromotionCoupon)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
@Table(name = "promotion_coupon")
@Entity
public class Coupon extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 142764991472035765L;
    /**
    * 主键
    */
    @Id
    private Long id;
    /**
    * 优惠券名称
    */
    private String name;
    /**
    * 优惠券类型，1：现金券，2：满减券
    */
    private Integer type;
    /**
    * 优惠券规则
    */
    private String rule;
    /**
    * 有效期开始时间
    */
    private Date startTime;
    /**
    * 有效期结束时间
    */
    private Date endTime;
    /**
    * 优惠券总量
    */
    private Long allCount;
    /**
    * 优惠券已经领取的数量
    */
    private Long receivedCount;
    /**
    * 优惠券发放方式，1：可发放可领取，2：仅可发放，3：仅可领取
    */
    private Integer giveType;
    /**
    * 优惠券状态，1：未开始；2：发放中，3：已发完；4：已过期
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}