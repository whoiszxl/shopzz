package com.whoiszxl.zero.entity.vo;

import com.whoiszxl.zero.bean.AbstractObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 营销推荐表(PromotionRecommend)实体类
 *
 * @author whoiszxl
 * @since 2021-04-09
 */
@Data
public class RecommendVO extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 244153366011353257L;

    @ApiModelProperty(value = "自增主键ID")
    private Integer id;

    @ApiModelProperty(value = "推荐商品ID")
    private Long productId;

    @ApiModelProperty(value = "推荐商品名称")
    private String productName;

    @ApiModelProperty(value = "默认图片")
    private String defaultPic;

    @ApiModelProperty(value = "默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty(value = "推荐商品类型 1:热门商品 2:精选商品")
    private Integer type;

    @ApiModelProperty(value = "上下线状态：0->下线；1->上线")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}