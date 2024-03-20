package com.whoiszxl.taowu.im.controller;


import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.im.cqrs.query.OfflineListQuery;
import com.whoiszxl.taowu.im.protocol.ChatMessage;
import com.whoiszxl.taowu.im.service.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-08-17
 */
@Tag(name = "IM消息 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;

    @PostMapping("/offline/list")
    @Operation(summary = "获取离线消息列表", description = "messageService")
    public ResponseResult<List<ChatMessage>> offlineList(@RequestBody OfflineListQuery query) {
        List<ChatMessage> result = messageService.listOfflineMessage(query);
        return ResponseResult.buildSuccess(result);
    }
}

