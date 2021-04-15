package com.whoiszxl.zero.dao;

import com.whoiszxl.zero.bean.BaseDao;
import com.whoiszxl.zero.entity.Recommend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * 商品推荐 dao
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
public interface RecommendDao extends BaseDao<Recommend, Long> {

    Page<Recommend> findAll(Specification<Recommend> var1, Pageable var2);
}
