package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * sku信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_sku")
@ApiModel(value = "Sku对象", description = "sku信息表")
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("sku主键ID")
      private Long id;

    @ApiModelProperty("商品SPU的ID")
    private Long spuId;

    @ApiModelProperty("所属分类id")
    private Long categoryId;

    @ApiModelProperty("父类目ID")
    private Long parentCategoryId;

    @ApiModelProperty("sku名称")
    private Long skuName;

    @ApiModelProperty("sku缩略图片地址")
    private String skuImg;

    @ApiModelProperty("销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty("促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty("商品销售属性,json格式")
    private String saleAttr;

    @ApiModelProperty("SKU编码")
    private String skuCode;

    @ApiModelProperty("乐观锁")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除 1: 已删除, 0: 未删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("创建者")
    private String createdBy;

    @ApiModelProperty("更新者")
    private String updatedBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
