package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.cqrs.command.CounterVideoCommand;
import com.whoiszxl.taowu.entity.VideoCounter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 视频计数表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
public interface IVideoCounterService extends IService<VideoCounter> {


    void blurryCounter(CounterVideoCommand command);

}
