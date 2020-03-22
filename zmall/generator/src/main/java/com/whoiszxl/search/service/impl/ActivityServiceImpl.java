package com.whoiszxl.search.service.impl;

import com.whoiszxl.search.entity.Activity;
import com.whoiszxl.search.mapper.ActivityMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.search.service.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-22
 */
@Slf4j
@Service
@Transactional
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

}
