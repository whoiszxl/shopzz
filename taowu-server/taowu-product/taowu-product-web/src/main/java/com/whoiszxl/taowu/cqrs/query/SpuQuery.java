package com.whoiszxl.taowu.cqrs.query;

import com.whoiszxl.taowu.common.annotation.Query;
import com.whoiszxl.taowu.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品基础信息表
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-21
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "商品基础信息表")
public class SpuQuery extends PageQuery {

    @Query(blurry = "name,sub_name,brand_name")
    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "类目ID")
    private Long categoryId;

    @Schema(description = "父类目ID")
    private Long parentCategoryId;

    @Schema(description = "品牌名称")
    private String brandName;

    @Schema(description = "删除状态:0->未删除; 1->已删除")
    private Integer deleteStatus;

    @Schema(description = "上架状态:0->下架; 1->上架")
    private Integer publishStatus;

    @Schema(description = "审核状态:0->未审核; 1->审核通过")
    private Integer verifyStatus;

}
