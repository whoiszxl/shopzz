package com.whoiszxl.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.user.entity.Areas;
import com.whoiszxl.user.mapper.AreasMapper;
import com.whoiszxl.user.service.AreasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 行政区域县区信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-25
 */
@Slf4j
@Service
@Transactional
public class AreasServiceImpl extends ServiceImpl<AreasMapper, Areas> implements AreasService {

}
