package com.whoiszxl.zero.repository;

import com.whoiszxl.zero.entity.HomeBanner;
import com.whoiszxl.zero.bean.BaseRepository;

import java.util.List;

/**
 * 主页轮播图repository
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
public interface HomeBannerRepository extends BaseRepository<HomeBanner> {

    /**
     * 通过类型和状态查找记录 按sort降序
     * @param type 类型 轮播位置：0->PC首页轮播；1->app首页轮播 2->app导航小组件
     * @param status 状态
     * @return
     */
    List<HomeBanner> findAllByTypeAndStatusOrderBySortDesc(Integer type, Integer status);
}
