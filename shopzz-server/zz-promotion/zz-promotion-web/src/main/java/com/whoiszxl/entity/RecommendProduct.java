package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 首页通用推荐商品表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-06
 */
@Getter
@Setter
@TableName("spms_recommend_product")
@ApiModel(value = "RecommendProduct对象", description = "首页通用推荐商品表")
public class RecommendProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增主键ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("SPU主键ID")
    private Long spuId;


}
