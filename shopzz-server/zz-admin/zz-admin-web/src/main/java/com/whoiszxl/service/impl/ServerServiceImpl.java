package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.Server;
import com.whoiszxl.mapper.ServerMapper;
import com.whoiszxl.service.ServerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务器表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2021-08-12
 */
@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements ServerService {

}
