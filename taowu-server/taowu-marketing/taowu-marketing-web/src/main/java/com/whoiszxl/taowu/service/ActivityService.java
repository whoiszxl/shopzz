package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.cqrs.response.ActivityApiResponse;
import com.whoiszxl.taowu.entity.Activity;
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
