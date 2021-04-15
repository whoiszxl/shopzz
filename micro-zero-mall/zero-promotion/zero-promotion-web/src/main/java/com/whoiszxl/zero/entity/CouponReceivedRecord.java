package com.whoiszxl.zero.entity;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.io.Serializable;

/**
 * 优惠券领取记录表(PromotionCouponReceivedRecord)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
@Table(name = "promotion_coupon_received_record")
@Entity
public class CouponReceivedRecord extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 370678469786864280L;
    /**
    * 主键ID
    */
    @Id
    private Long id;
    /**
    * 优惠券ID
    */
    private Long couponId;
    /**
    * 用户账号ID
    */
    private Long memberId;
    /**
    * 是否使用过这个优惠券，1：使用了，0：未使用
    */
    private Integer isUsed;
    /**
    * 使用优惠券的时间
    */
    private Date usedTime;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}