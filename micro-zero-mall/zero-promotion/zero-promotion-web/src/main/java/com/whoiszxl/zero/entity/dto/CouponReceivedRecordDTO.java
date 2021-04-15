package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券领取记录表(PromotionCouponReceivedRecord)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class CouponReceivedRecordDTO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 370678469786864280L;
    /**
    * 主键ID
    */
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