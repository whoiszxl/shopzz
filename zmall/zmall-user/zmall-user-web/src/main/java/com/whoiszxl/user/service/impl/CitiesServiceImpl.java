package com.whoiszxl.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.user.entity.Cities;
import com.whoiszxl.user.mapper.CitiesMapper;
import com.whoiszxl.user.service.CitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 行政区域地州市信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Slf4j
@Service
@Transactional
public class CitiesServiceImpl extends ServiceImpl<CitiesMapper, Cities> implements CitiesService {

}
