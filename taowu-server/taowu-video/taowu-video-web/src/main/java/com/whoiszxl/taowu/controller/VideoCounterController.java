package com.whoiszxl.taowu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.cqrs.command.CounterVideoCommand;
import com.whoiszxl.taowu.entity.Video;
import com.whoiszxl.taowu.service.IVideoCounterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 视频计数表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Tag(name = "C端：counter视频计数 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video-counter")
public class VideoCounterController {

    private final IVideoCounterService videoCounterService;



    @PostMapping("/blurry")
    @Operation(summary = "观看、转发、点赞 - 模糊计数", description = "观看、转发、点赞 - 模糊计数")
    public ResponseResult<PageResponse<Video>> blurryCounter(@RequestBody CounterVideoCommand command) {
        videoCounterService.blurryCounter(command);
        return ResponseResult.buildSuccess();
    }


}

