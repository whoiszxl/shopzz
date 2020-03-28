package com.whoiszxl.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.product.entity.Sku;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
public interface SkuService extends IService<Sku> {

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Sku> findList(Map<String, Object> searchMap);

    /**
     * 扣减库存
     * @param username
     */
    void decrCount(String username);
}
