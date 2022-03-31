package com.whoiszxl.service;

import com.whoiszxl.cqrs.response.ActivityApiResponse;
import com.whoiszxl.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 促销活动表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
public interface ActivityService extends IService<Activity> {

    /**
     * 通过活动ID查询活动详情
     * @param id
     * @return
     */
    ActivityApiResponse detail(Long id);
}
