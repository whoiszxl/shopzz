package com.whoiszxl.taowu.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.im.cqrs.command.TalkAddCommand;
import com.whoiszxl.taowu.im.cqrs.command.TalkDeleteCommand;
import com.whoiszxl.taowu.im.cqrs.query.TalkQuery;
import com.whoiszxl.taowu.im.cqrs.response.TalkResponse;
import com.whoiszxl.taowu.im.entity.Talk;
import com.whoiszxl.taowu.im.pack.PrivateChatReadPack;
import com.whoiszxl.zhipin.tools.common.entity.response.PageResponse;

/**
 * <p>
 * 对话表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-18
 */
public interface ITalkService extends IService<Talk> {

    /**
     * 设置已读操作
     * @param privateChatReadPack 已读包
     */
    void read(PrivateChatReadPack privateChatReadPack);


    /**
     * 添加对话框
     * @return 是否添加成功
     */
    Boolean add(TalkAddCommand command);

    /**
     * 删除对话框
     * @param command 删除命令
     * @return 是否删除成功
     */
    Boolean delete(TalkDeleteCommand command);

    /**
     * 获取对话框列表
     * @param talkQuery
     * @return
     */
    PageResponse<TalkResponse> talkList(TalkQuery talkQuery);
}
