package com.whoiszxl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.bean.PageQuery;
import com.whoiszxl.cqrs.response.HomeRecommendVO;
import com.whoiszxl.entity.RecommendProduct;

import java.util.List;

/**
 * <p>
 * 首页通用推荐商品表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-06
 */
public interface RecommendProductService extends IService<RecommendProduct> {

    /**
     * 分页获取首页推荐列表
     * @param query
     * @return
     */
    List<HomeRecommendVO> getHomeRecommendList(PageQuery query);


    /**
     * 刷新推荐商品列表
     */
    void homeRecommendRefresh();


}
