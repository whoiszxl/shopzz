package com.whoiszxl.taowu.controller;

import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.cqrs.command.CounterVideoCommand;
import com.whoiszxl.taowu.service.IBlurryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2023-06-29
 */
@Slf4j
@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Tag(name = "feign 端：视频计数 API")
public class CounterVideoController {

    private final IBlurryService blurryService;

    @PostMapping("/blurry")
    @Operation(summary = "模糊计数", description = "模糊计数，计数可能存在一定误差，读写请求高并发，例如点赞、转发等等")
    public ResponseResult<Boolean> videoBlurry(@RequestBody CounterVideoCommand command) {
        blurryService.videoBlurry(command);
        return ResponseResult.buildSuccess();
    }
}

