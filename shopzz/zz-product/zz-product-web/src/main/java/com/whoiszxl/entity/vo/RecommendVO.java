package com.whoiszxl.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 营销推荐表
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Recommend对象", description="营销推荐表")
public class RecommendVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
    private Integer id;

    @ApiModelProperty("推荐商品ID")
    private Long productId;

    @ApiModelProperty("推荐商品名称")
    private String productName;

    @ApiModelProperty("默认图片")
    private String defaultPic;

    @ApiModelProperty("默认价格")
    private BigDecimal defaultPrice;

    @ApiModelProperty("推荐商品类型 1:热门商品 2:精选商品")
    private Boolean type;

    @ApiModelProperty("上下线状态：0->下线；1->上线")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;

}
