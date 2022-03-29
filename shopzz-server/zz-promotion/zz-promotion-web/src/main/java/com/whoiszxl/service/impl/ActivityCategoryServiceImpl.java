package com.whoiszxl.service.impl;

import com.whoiszxl.entity.ActivityCategory;
import com.whoiszxl.mapper.ActivityCategoryMapper;
import com.whoiszxl.service.ActivityCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 促销活动跟分类的关联关系表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-03-29
 */
@Service
public class ActivityCategoryServiceImpl extends ServiceImpl<ActivityCategoryMapper, ActivityCategory> implements ActivityCategoryService {

}
