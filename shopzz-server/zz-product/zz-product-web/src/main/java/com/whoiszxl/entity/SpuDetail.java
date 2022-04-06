package com.whoiszxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品详情页数据表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Getter
@Setter
@TableName("pms_spu_detail")
@ApiModel(value = "SpuDetail对象", description = "商品详情页数据表")
public class SpuDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品ID")
    private Long spuId;

    @ApiModelProperty("PC商品详情富文本内容")
    private String detailHtml;

    @ApiModelProperty("移动端商品详情富文本内容")
    private String detailMobile;


}
