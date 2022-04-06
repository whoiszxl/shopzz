package com.whoiszxl.cqrs.response;

import com.whoiszxl.cqrs.vo.SkuStockVO;
import com.whoiszxl.cqrs.vo.SkuVO;
import com.whoiszxl.cqrs.vo.SpuImagesVO;
import com.whoiszxl.cqrs.vo.SpuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * SPU详情返回实体
 *
 * @author whoiszxl
 * @date 2022/4/6
 */
@Data
@ApiModel(value="SPU详情返回实体", description="SPU详情返回实体")
public class SpuDetailResponse {

    @ApiModelProperty("商品SPU基本信息")
    private SpuVO spuVO;

    @ApiModelProperty("商品SKU基本信息")
    private List<SkuVO> skus;

    @ApiModelProperty("商品SKU基本信息")
    private List<SkuStockVO> skuStocks;

    @ApiModelProperty("商品SPU图片")
    private List<SpuImagesVO> images;

}
