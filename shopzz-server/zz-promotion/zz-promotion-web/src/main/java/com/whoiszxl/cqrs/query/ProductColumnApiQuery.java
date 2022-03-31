package com.whoiszxl.cqrs.query;

import com.whoiszxl.bean.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品专栏表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Getter
@Setter
@ApiModel(value = "ProductColumn对象", description = "商品专栏表")
public class ProductColumnApiQuery {

    @NotNull(message = "专栏ID不允许为空")
    @ApiModelProperty("专栏ID")
    private Long id;

}
