package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.cqrs.command.CounterMemberCommand;
import com.whoiszxl.taowu.cqrs.command.CounterVideoCommand;

/**
 * 模糊计数服务接口
 * @author whoiszxl
 */
public interface IBlurryService {

    /**
     * 会员模糊计数
     * @param command 计数参数
     */
    void memberBlurry(CounterMemberCommand command);

    /**
     * 视频模糊计数
     * @param command 计数参数
     */
    void videoBlurry(CounterVideoCommand command);
}
