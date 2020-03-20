package com.whoiszxl.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.product.entity.MallBrand;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
public interface MallBrandService extends IService<MallBrand> {

    /**
     * 根据商品分类名称查询品牌列表
     * @param categoryName 商品分类名称
     * @return
     */
    List<Map> findBrandListByCategoryName(String categoryName);
}
