package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.SendLog;
import com.whoiszxl.mapper.SendLogMapper;
import com.whoiszxl.service.SendLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信发送日志表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class SendLogServiceImpl extends ServiceImpl<SendLogMapper, SendLog> implements SendLogService {

}
