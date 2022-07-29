package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Software;
import com.whoiszxl.mapper.SoftwareMapper;
import com.whoiszxl.service.SoftwareService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础组件表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-13
 */
@Service
public class SoftwareServiceImpl extends ServiceImpl<SoftwareMapper, Software> implements SoftwareService {

    @Override
    public Software getBySoftwareName(String softwareName) {
        LambdaQueryWrapper<Software> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Software::getSoftwareName, softwareName);
        return this.getOne(queryWrapper);
    }
}
