package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.entity.TimingPush;
import com.whoiszxl.mapper.TimingPushMapper;
import com.whoiszxl.service.TimingPushService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信定时发送表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-05-26
 */
@Service
public class TimingPushServiceImpl extends ServiceImpl<TimingPushMapper, TimingPush> implements TimingPushService {

}
