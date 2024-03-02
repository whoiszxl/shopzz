package com.whoiszxl.taowu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.cqrs.command.VideoPublishCommand;
import com.whoiszxl.taowu.cqrs.query.MemberTimelineQuery;
import com.whoiszxl.taowu.cqrs.response.VideoResponse;
import com.whoiszxl.taowu.entity.Video;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
public interface IVideoService extends IService<Video> {

    /**
     * 视频发布
     * @param command 发布命令
     */
    void publish(VideoPublishCommand command);

    /**
     * 视频点赞
     * @param videoId 视频ID
     */
    void like(Long videoId);

    /**
     * 取消点赞
     * @param videoId 视频ID
     */
    void disLike(Long videoId);

    /**
     * 分页查询当前用户的关注用户视频feed流列表
     * @param pageQuery
     * @return
     */
    IPage<VideoResponse> attentionFeedList(PageQuery pageQuery);

    /**
     * 分页查询当前用户的推荐视频feed流列表
     * @param pageQuery
     * @return
     */
    IPage<VideoResponse> recommendFeedList(PageQuery pageQuery);

    /**
     * 分页查询指定用户的视频列表
     * @param memberTimelineQuery
     * @return
     */
    IPage<VideoResponse> timeline(MemberTimelineQuery memberTimelineQuery);

    /**
     * 指定ID查询视频详情
     * @param videoId
     * @return
     */
    VideoResponse getVideoById(String videoId);

    /**
     * 分页查询当前登录用户的视频列表
     * @param pageQuery
     * @return
     */
    IPage<VideoResponse> my(PageQuery pageQuery);
}
