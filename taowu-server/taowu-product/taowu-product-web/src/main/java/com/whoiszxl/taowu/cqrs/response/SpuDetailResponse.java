package com.whoiszxl.taowu.cqrs.response;

import com.whoiszxl.taowu.cqrs.vo.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * SPU详情返回实体
 *
 * @author whoiszxl
 * @date 2022/4/6
 */
@Data
@Schema(description = "SPU详情返回实体")
public class SpuDetailResponse {

    @Schema(description = "商品SPU基本信息")
    private SpuVO spuVO;

    @Schema(description = "商品SKU基本信息")
    private List<SkuVO> skus;

    @Schema(description = "商品SKU基本信息")
    private List<SkuStockVO> skuStocks;

    @Schema(description = "商品SPU图片")
    private List<SpuImagesVO> images;

    @Schema(description = "商品SPU详情描述信息")
    private SpuDetailVO spuDetailVO;

    @Schema(description = "SPU属性组返回VO实体")
    private List<SpuAttributeGroupVO> spuAttributeGroupVOList;

}
