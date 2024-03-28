package com.whoiszxl.taowu.controller;


import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.cqrs.command.CommentDeleteCommand;
import com.whoiszxl.taowu.cqrs.command.CommentPublishCommand;
import com.whoiszxl.taowu.cqrs.command.CommentTopCommand;
import com.whoiszxl.taowu.cqrs.query.CommentQuery;
import com.whoiszxl.taowu.cqrs.response.CommentResponse;
import com.whoiszxl.taowu.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 评论主表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2024-03-26
 */
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentApiController {

    private final ICommentService commentService;


    @PostMapping("/publish")
    @Operation(summary = "评论发布", description = "评论发布")
    public ResponseResult<Boolean> publishComment(@RequestBody CommentPublishCommand command) {
        commentService.publishComment(command);
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除自己的评论和自己评论下的其他评论", description = "删除自己的评论和自己评论下的其他评论")
    public ResponseResult<Boolean> deleteComment(@RequestBody CommentDeleteCommand command) {
        commentService.deleteComment(command);
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/top")
    @Operation(summary = "评论置顶", description = "评论置顶")
    public ResponseResult<Boolean> topComment(@RequestBody CommentTopCommand command) {
        commentService.topComment(command);
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/query")
    @Operation(summary = "查询一级或者二级评论", description = "查询一级或者二级评论")
    public ResponseResult<PageResponse<CommentResponse>> queryComment(@RequestBody CommentQuery query) {
        PageResponse<CommentResponse> results = commentService.queryComment(query);
        return ResponseResult.buildSuccess(results);
    }


    // 评论点赞

    // 评论点踩

}

