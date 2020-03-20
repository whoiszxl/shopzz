package com.whoiszxl.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.product.entity.Spec;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
public interface SpecService extends IService<Spec> {

    /**
     * 根据商品分类名称查询规格列表
     * @param categoryName
     * @return
     */
    public List<Map> findSpecListByCategoryName(String categoryName);
}
