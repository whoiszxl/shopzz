package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.cqrs.command.LikeCommand;
import com.whoiszxl.taowu.cqrs.command.VideoPublishCommand;
import com.whoiszxl.taowu.cqrs.query.MemberTimelineQuery;
import com.whoiszxl.taowu.cqrs.response.VideoResponse;
import com.whoiszxl.taowu.entity.Video;
import com.whoiszxl.taowu.enums.LikeTypeEnum;
import com.whoiszxl.taowu.enums.VideoCounterStatusEnum;
import com.whoiszxl.taowu.mapper.VideoMapper;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.taowu.service.IVideoService;
import com.whoiszxl.taowu.strategy.LikeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2023-06-13
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    private final IdWorker idWorker;

    private final LikeFactory likeFactory;

    private final MemberFeignClient memberApiClient;

    private final TokenHelper tokenHelper;

    @Override
    public void publish(VideoPublishCommand command) {
        Long memberId = tokenHelper.getAppMemberId();
        Video video = BeanUtil.copyProperties(command, Video.class);
        video.setId(idWorker.nextId());
        video.setMemberId(memberId);
        this.save(video);
    }

    @Override
    public void like(Long videoId) {
        Long memberId = tokenHelper.getAppMemberId();

        LikeCommand likeCommand = new LikeCommand();
        likeCommand.setId(videoId);
        likeCommand.setMemberId(memberId);
        likeCommand.setLikeType(LikeTypeEnum.VIDEO.getCode());
        likeCommand.setStatus(VideoCounterStatusEnum.INCR.getCode());

        likeFactory.getLikeStrategy(likeCommand.getLikeType()).like(likeCommand);

    }

    @Override
    public void disLike(Long videoId) {
        Long memberId = tokenHelper.getAppMemberId();

        LikeCommand likeCommand = new LikeCommand();
        likeCommand.setId(videoId);
        likeCommand.setMemberId(memberId);
        likeCommand.setLikeType(LikeTypeEnum.VIDEO.getCode());
        likeCommand.setStatus(VideoCounterStatusEnum.DECR.getCode());

        likeFactory.getLikeStrategy(likeCommand.getLikeType()).like(likeCommand);
    }

    /**
     * 使用pull+push结合的模式
     *
     * 1. 先读取关注的热点用户列表，循环获取其timeline中的前10条视频
     * 2. 同时读取自己的收件箱视频列表
     * 3. 合并上述获取的列表，并按时间排序返回
     *
     * 广告实现：
     * 1. 通过大数据实时分析用户标签，比如说：单机游戏，粤语流行乐
     * 2. 推广客户创建出对应的账号，如：泰拉瑞亚官方账号
     * 3. 将具有对应标签的用户隐性关注此推广账号
     * 4. 此时广告视频也会存在与用户的feed推荐流里
     * @param pageQuery
     * @return
     */
    @Override
    public IPage<VideoResponse> attentionFeedList(PageQuery pageQuery) {
        return null;
    }

    @Override
    public IPage<VideoResponse> recommendFeedList(PageQuery pageQuery) {
        Long memberId = tokenHelper.getAppMemberId();
        //TODO 推荐实现
        LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Video::getCreatedAt);
        Page<Video> videoPage = this.page(new Page<>(pageQuery.getPage(), pageQuery.getSize()), lambdaQueryWrapper);

        if(videoPage.getRecords().isEmpty()) {
            return null;
        }

        List<Long> memberIdList = videoPage.getRecords().stream().map(Video::getMemberId).distinct().collect(Collectors.toList());
        List<MemberDTO> memberDTOList = memberApiClient.findMemberInfoByIds(new ArrayList<Long>(){{
            addAll(memberIdList);
        }});

        return videoPage.convert(video -> {
            VideoResponse videoResponse = BeanUtil.copyProperties(video, VideoResponse.class);
            MemberDTO memberDTO = memberDTOList.stream().filter(e -> e.getId().equals(videoResponse.getMemberId())).findAny().get();
            videoResponse.setMemberId(memberDTO.getId());

            VideoResponse.UploaderInfo uploaderInfo = VideoResponse.UploaderInfo.builder()
                            .avatar(memberDTO.getAvatar())
                            .nickname(memberDTO.getFullName())
                            .build();
            videoResponse.setUploaderInfo(uploaderInfo);
            return videoResponse;
        });
    }

    @Override
    public IPage<VideoResponse> timeline(MemberTimelineQuery memberTimelineQuery) {
        Long currentMemberId = tokenHelper.getAppMemberId();
        Long finalCurrentMemberId = currentMemberId;

        LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Video::getCreatedAt);
        lambdaQueryWrapper.eq(Video::getMemberId, memberTimelineQuery.getMemberId());
        Page<Video> videoPage = this.page(new Page<>(memberTimelineQuery.getPage(), memberTimelineQuery.getSize()), lambdaQueryWrapper);
        if(videoPage.getRecords().isEmpty()) {
            return null;
        }

        List<Long> memberIdList = videoPage.getRecords().stream().map(Video::getMemberId).distinct().collect(Collectors.toList());
        List<MemberDTO> memberDTOList = memberApiClient.findMemberInfoByIds(memberIdList);

        return videoPage.convert(video -> {
            VideoResponse videoResponse = BeanUtil.copyProperties(video, VideoResponse.class);
            MemberDTO memberDTO = memberDTOList.stream().filter(e -> e.getId().equals(videoResponse.getMemberId())).findAny().get();
            videoResponse.setMemberId(memberDTO.getId());

            VideoResponse.UploaderInfo uploaderInfo = VideoResponse.UploaderInfo.builder()
                    .avatar(memberDTO.getAvatar())
                    .nickname(memberDTO.getFullName())
                    .build();
            videoResponse.setUploaderInfo(uploaderInfo);

            VideoResponse.CounterInfo counterInfo = VideoResponse.CounterInfo.builder()
                    .commentCount(likeFactory.getLikeStrategy(LikeTypeEnum.COMMENT.getCode()).getLikeCount(video.getId()))
                    .lickCount(likeFactory.getLikeStrategy(LikeTypeEnum.VIDEO.getCode()).getLikeCount(video.getId()))
                    .hasLiked(likeFactory.getLikeStrategy(LikeTypeEnum.VIDEO.getCode()).isLike(video.getId(), finalCurrentMemberId))
                    .shareCount(1227)
                    .build();
            videoResponse.setCounterInfo(counterInfo);
            return videoResponse;
        });
    }
    @Override
    public VideoResponse getVideoById(String videoId) {
        Video video = this.getOne(Wrappers.<Video>lambdaQuery().eq(Video::getId, videoId));

        List<Long> params = new ArrayList<>();
        params.add(video.getMemberId());
        List<MemberDTO> memberDTOList = memberApiClient.findMemberInfoByIds(params);

        VideoResponse videoResponse = BeanUtil.copyProperties(video, VideoResponse.class);

        MemberDTO memberDTO = memberDTOList.get(0);
        videoResponse.setMemberId(memberDTO.getId());

        VideoResponse.UploaderInfo uploaderInfo = VideoResponse.UploaderInfo.builder()
                .avatar(memberDTO.getAvatar())
                .nickname(memberDTO.getFullName())
                .build();
        videoResponse.setUploaderInfo(uploaderInfo);

        VideoResponse.CounterInfo counterInfo = VideoResponse.CounterInfo.builder()
                .commentCount(likeFactory.getLikeStrategy(LikeTypeEnum.COMMENT.getCode()).getLikeCount(video.getId()))
                .lickCount(likeFactory.getLikeStrategy(LikeTypeEnum.VIDEO.getCode()).getLikeCount(video.getId()))
                .shareCount(1227)
                .build();
        videoResponse.setCounterInfo(counterInfo);

        return videoResponse;
    }

    @Override
    public IPage<VideoResponse> my(PageQuery pageQuery) {
        Long currentMemberId = tokenHelper.getAppMemberId();

        LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Video::getCreatedAt);
        lambdaQueryWrapper.eq(Video::getMemberId, currentMemberId);
        Page<Video> videoPage = this.page(new Page<>(pageQuery.getPage(), pageQuery.getSize()),
                Wrappers.<Video>lambdaQuery()
                        .eq(Video::getMemberId, currentMemberId)
                        .orderByDesc(Video::getCreatedAt));
        if(videoPage.getRecords().isEmpty()) {
            return null;
        }

        return videoPage.convert(video -> {
            VideoResponse videoResponse = BeanUtil.copyProperties(video, VideoResponse.class);
            VideoResponse.CounterInfo counterInfo = VideoResponse.CounterInfo.builder()
                    .commentCount(likeFactory.getLikeStrategy(LikeTypeEnum.COMMENT.getCode()).getLikeCount(video.getId()))
                    .lickCount(likeFactory.getLikeStrategy(LikeTypeEnum.VIDEO.getCode()).getLikeCount(video.getId()))
                    .hasLiked(likeFactory.getLikeStrategy(LikeTypeEnum.VIDEO.getCode()).isLike(video.getId(), currentMemberId))
                    .shareCount(1227)
                    .build();
            videoResponse.setCounterInfo(counterInfo);
            return videoResponse;
        });
    }
}
