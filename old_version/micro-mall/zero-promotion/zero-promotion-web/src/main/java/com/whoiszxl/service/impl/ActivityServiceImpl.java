package com.whoiszxl.service.impl;

import com.whoiszxl.entity.Activity;
import com.whoiszxl.mapper.ActivityMapper;
import com.whoiszxl.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 促销活动表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-07-28
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

}
