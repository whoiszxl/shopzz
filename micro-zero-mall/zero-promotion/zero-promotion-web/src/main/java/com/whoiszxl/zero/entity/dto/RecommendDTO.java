package com.whoiszxl.zero.entity.dto;

import com.whoiszxl.zero.bean.AbstractObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 营销推荐表(PromotionRecommend)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class RecommendDTO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 244153366011353257L;
    /**
    * 自增主键ID
    */
    private Integer id;
    /**
    * 推荐商品ID
    */
    private Long productId;
    /**
    * 推荐商品名称
    */
    private String productName;
    /**
     * 默认图片
     */
    private String defaultPic;
    /**
     * 默认价格
     */
    private BigDecimal defaultPrice;
    /**
    * 推荐商品类型 1:热门商品 2:精选商品
    */
    private Integer type;
    /**
    * 上下线状态：0->下线；1->上线
    */
    private Integer status;
    /**
    * 排序
    */
    private Integer sort;
    /**
    * 创建时间
    */
    private Date createdAt;
    /**
    * 更新时间
    */
    private Date updatedAt;

}