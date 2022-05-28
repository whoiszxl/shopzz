package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.ManualTask;
import com.whoiszxl.mapper.ManualTaskMapper;
import com.whoiszxl.service.ManualTaskService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信人工处理任务表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class ManualTaskServiceImpl extends ServiceImpl<ManualTaskMapper, ManualTask> implements ManualTaskService {

}
