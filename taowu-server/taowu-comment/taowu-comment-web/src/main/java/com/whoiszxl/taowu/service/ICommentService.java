package com.whoiszxl.taowu.service;

import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.cqrs.command.CommentDeleteCommand;
import com.whoiszxl.taowu.cqrs.command.CommentPublishCommand;
import com.whoiszxl.taowu.cqrs.command.CommentTopCommand;
import com.whoiszxl.taowu.cqrs.query.CommentQuery;
import com.whoiszxl.taowu.cqrs.response.CommentResponse;
import com.whoiszxl.taowu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论主表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-26
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 发布一条评论
     * @param command 发布命令
     */
    void publishComment(CommentPublishCommand command);

    void deleteComment(CommentDeleteCommand command);

    void topComment(CommentTopCommand command);

    PageResponse<CommentResponse> queryComment(CommentQuery query);
}
