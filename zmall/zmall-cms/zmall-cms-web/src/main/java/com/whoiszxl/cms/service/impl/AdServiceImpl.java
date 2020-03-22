package com.whoiszxl.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.cms.entity.Ad;
import com.whoiszxl.cms.mapper.AdMapper;
import com.whoiszxl.cms.service.AdService;
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
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

}
