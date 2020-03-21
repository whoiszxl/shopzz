package com.whoiszxl.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.system.entity.ResourceMenu;
import com.whoiszxl.system.mapper.ResourceMenuMapper;
import com.whoiszxl.system.service.ResourceMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-21
 */
@Slf4j
@Service
@Transactional
public class ResourceMenuServiceImpl extends ServiceImpl<ResourceMenuMapper, ResourceMenu> implements ResourceMenuService {

}
