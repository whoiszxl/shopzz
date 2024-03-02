package com.whoiszxl.taowu.im.controller;


import com.whoiszxl.taowu.im.cqrs.command.TalkAddCommand;
import com.whoiszxl.taowu.im.cqrs.command.TalkDeleteCommand;
import com.whoiszxl.taowu.im.cqrs.response.TalkResponse;
import com.whoiszxl.taowu.im.cqrs.query.TalkQuery;
import com.whoiszxl.taowu.im.service.ITalkService;
import com.whoiszxl.zhipin.tools.common.entity.ResponseResult;
import com.whoiszxl.zhipin.tools.common.entity.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 对话表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-18
 */
@Tag(name = "对话框 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/talk")
public class TalkController {

    private final ITalkService talkService;


    @GetMapping("/list")
    @Operation(summary = "全量获取对话框列表", description = "全量获取对话框列表")
    public ResponseResult<PageResponse<TalkResponse>> list(@Validated TalkQuery talkQuery) {
        PageResponse<TalkResponse> pageResponse = talkService.talkList(talkQuery);
        return ResponseResult.buildSuccess(pageResponse);
    }

    @PostMapping("/add")
    @Operation(summary = "添加对话框", description = "添加对话框")
    public ResponseResult<Boolean> add(@Validated @RequestBody TalkAddCommand command) {
        Boolean flag = talkService.add(command);
        return ResponseResult.buildByFlag(flag);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除对话框", description = "删除对话框")
    public ResponseResult<Boolean> delete(@Validated @RequestBody TalkDeleteCommand command) {
        Boolean flag = talkService.delete(command);
        return ResponseResult.buildByFlag(flag);
    }



}

