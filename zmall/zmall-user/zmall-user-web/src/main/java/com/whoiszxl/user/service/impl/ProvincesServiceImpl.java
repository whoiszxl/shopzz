package com.whoiszxl.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.user.entity.Provinces;
import com.whoiszxl.user.mapper.ProvincesMapper;
import com.whoiszxl.user.service.ProvincesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 省份信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Slf4j
@Service
@Transactional
public class ProvincesServiceImpl extends ServiceImpl<ProvincesMapper, Provinces> implements ProvincesService {

}
