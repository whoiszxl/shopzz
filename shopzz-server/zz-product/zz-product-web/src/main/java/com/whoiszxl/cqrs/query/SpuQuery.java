package com.whoiszxl.cqrs.query;

import com.baomidou.mybatisplus.annotation.*;
import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品基础信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@ApiModel(value = "Spu对象", description = "商品基础信息表")
public class SpuQuery extends PageQuery {

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("类目ID")
    private Long categoryId;

    @ApiModelProperty("父类目ID")
    private Long parentCategoryId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("删除状态:0->未删除; 1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty("上架状态:0->下架; 1->上架")
    private Integer publishStatus;

    @ApiModelProperty("审核状态:0->未审核; 1->审核通过")
    private Integer verifyStatus;

}
