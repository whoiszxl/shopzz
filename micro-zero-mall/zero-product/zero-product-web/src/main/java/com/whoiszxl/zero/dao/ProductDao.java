package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.bean.BaseDao;
import com.whoiszxl.zero.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public interface ProductDao extends BaseDao<Product, Long> {

    /**
     * 分页多条件查询所有记录
     * @param var1
     * @param var2
     * @return
     */
    Page<Product> findAll(@Nullable Specification<Product> var1, Pageable var2);
}
