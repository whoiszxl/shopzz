package com.whoiszxl.taowu.service.impl;

import com.whoiszxl.taowu.cqrs.command.CounterVideoCommand;
import com.whoiszxl.taowu.entity.VideoCounter;
import com.whoiszxl.taowu.mapper.VideoCounterMapper;
import com.whoiszxl.taowu.service.IVideoCounterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频计数表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Service
public class VideoCounterServiceImpl extends ServiceImpl<VideoCounterMapper, VideoCounter> implements IVideoCounterService {

    @Override
    public void blurryCounter(CounterVideoCommand command) {

    }
}
