package com.whoiszxl.taowu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.entity.ZSetPageQuery;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.common.entity.response.SetPageResponse;
import com.whoiszxl.taowu.cqrs.command.VideoPublishCommand;
import com.whoiszxl.taowu.cqrs.query.MemberTimelineQuery;
import com.whoiszxl.taowu.cqrs.response.VideoResponse;
import com.whoiszxl.taowu.entity.Video;
import com.whoiszxl.taowu.service.IVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */

@Tag(name = "C端：video视频 API")
@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final IVideoService videoService;

    @PostMapping("/latest/test")
    @Operation(summary = "获取最新的视频列表-测试接口", description = "测试APP接入使用")
    public ResponseResult<PageResponse<Video>> latestTest(@RequestBody PageQuery pageQuery) {
        Page<Video> page = videoService.page(new Page<>(pageQuery.getPage(), pageQuery.getSize()));
        PageResponse<Video> pageResponse = PageResponse.convert(page, Video.class);
        return ResponseResult.buildSuccess(pageResponse);
    }

    @PostMapping("/publish")
    @Operation(summary = "视频发布", description = "视频发布")
    public ResponseResult<List<Video>> publish(@RequestBody VideoPublishCommand command) {
        videoService.publish(command);
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/pull/attention/feed")
    @Operation(summary = "拉取关注用户的Feed", description = "用户需要定期去拉取自己关注的热门用户的outbox")
    public ResponseResult<List<Video>> pullAttentionFeed() {
        videoService.pullAttentionFeed();
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/attention/feed/list")
    @Operation(summary = "分页查询当前用户的关注用户视频feed流列表", description = "分页查询当前用户的关注用户视频feed流列表")
    public ResponseResult<SetPageResponse<VideoResponse>> attentionFeedList(@RequestBody ZSetPageQuery pageQuery) {
        SetPageResponse<VideoResponse> response = videoService.attentionFeedList(pageQuery);
        return ResponseResult.buildSuccess(response);
    }

    @PostMapping("/recommend/feed/list")
    @Operation(summary = "分页查询当前用户的推荐视频feed流列表", description = "分页查询当前用户的推荐视频feed流列表")
    public ResponseResult<List<VideoResponse>> recommendFeedList(@RequestBody PageQuery pageQuery) {
        IPage<VideoResponse> videoPage = videoService.recommendFeedList(pageQuery);
        return ResponseResult.buildSuccess(videoPage.getRecords());
    }

    @GetMapping("/{videoId}")
    @Operation(summary = "指定ID查询视频", description = "指定ID查询视频")
    public ResponseResult<VideoResponse> getVideoById(@PathVariable String videoId) {
        VideoResponse videoResponse = videoService.getVideoById(videoId);
        return ResponseResult.buildSuccess(videoResponse);
    }

    @PostMapping("/timeline/list")
    @Operation(summary = "分页查询指定用户的视频列表", description = "分页查询指定用户的视频列表")
    public ResponseResult<List<VideoResponse>> timeline(@RequestBody MemberTimelineQuery memberTimelineQuery) {
        IPage<VideoResponse> videoPage = videoService.timeline(memberTimelineQuery);
        return ResponseResult.buildSuccess(videoPage.getRecords());
    }


    @PostMapping("/my")
    @Operation(summary = "分页查询当前登录用户的视频列表", description = "分页查询当前登录用户的视频列表")
    public ResponseResult<List<VideoResponse>> my(@RequestBody PageQuery pageQuery) {
        IPage<VideoResponse> videoPage = videoService.my(pageQuery);
        return ResponseResult.buildSuccess(videoPage.getRecords());
    }

    @PostMapping("/like/{videoId}")
    @Operation(summary = "点赞", description = "点赞")
    public ResponseResult<Boolean> like(@PathVariable Long videoId) {
        videoService.like(videoId);
        return ResponseResult.buildSuccess();
    }

    @PostMapping("/dislike/{videoId}")
    @Operation(summary = "取消点赞", description = "取消点赞")
    public ResponseResult<Boolean> dislike(@PathVariable Long videoId) {
        videoService.disLike(videoId);
        return ResponseResult.buildSuccess();
    }

}

