package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.ReceiveLog;
import com.whoiszxl.mapper.ReceiveLogMapper;
import com.whoiszxl.service.ReceiveLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信接收日志表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class ReceiveLogServiceImpl extends ServiceImpl<ReceiveLogMapper, ReceiveLog> implements ReceiveLogService {

}
