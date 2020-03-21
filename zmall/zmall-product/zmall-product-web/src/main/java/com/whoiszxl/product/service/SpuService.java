package com.whoiszxl.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.product.entity.Product;
import com.whoiszxl.product.entity.Spu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-03-20
 */
public interface SpuService extends IService<Spu> {

    /**
     * 新增商品
     * @param product
     */
    void add(Product product);

    /**
     * 更新商品
     * @param product
     */
    void update(Product product);

    /**
     * 通过id查找商品
     * @param id
     * @return
     */
    Product findProductById(String id);

    /**
     * 审核商品
     * @param id
     */
    void audit(String id);

    /**
     * 下架商品
     * @param id
     */
    void pull(String id);

    /**
     * 上架商品
     * @param id
     */
    void put(String id);

    /**
     * 恢复商品
     * @param id
     */
    void restore(String id);

    /**
     * 物理删除，无法恢复。必须软删除后才能硬删除。
     * @param id
     */
    void readDelete(String id);
}
