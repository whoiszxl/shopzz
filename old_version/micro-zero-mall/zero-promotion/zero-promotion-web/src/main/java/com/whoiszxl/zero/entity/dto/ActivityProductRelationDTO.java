package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 促销活动跟商品sku的关联关系表(PromotionActivityProductRelation)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class ActivityProductRelationDTO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = -28462230423574462L;
    /**
    * 自增主键ID
    */
    private Integer id;
    /**
    * 促销活动ID
    */
    private Long promotionActivityId;
    /**
    * 关联的某个商品sku的ID，如果将这个字段的值设置为-1，那么代表针对全部商品
    */
    private Long productId;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}