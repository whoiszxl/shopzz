package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.entity.ActivityCategory;
import com.whoiszxl.taowu.mapper.ActivityCategoryMapper;
import com.whoiszxl.taowu.service.ActivityCategoryService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ActivityCategoryServiceImpl extends ServiceImpl<ActivityCategoryMapper, ActivityCategory> implements ActivityCategoryService {

}
