package com.whoiszxl.taowu.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.constants.RocketMQConstant;
import com.whoiszxl.taowu.common.entity.PageQuery;
import com.whoiszxl.taowu.common.entity.ZSetPageQuery;
import com.whoiszxl.taowu.common.entity.response.PageResponse;
import com.whoiszxl.taowu.common.entity.response.SetPageResponse;
import com.whoiszxl.taowu.common.enums.CounterDimTypeEnum;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.token.entity.AppLoginMember;
import com.whoiszxl.taowu.common.utils.BeanUtil;
import com.whoiszxl.taowu.common.utils.IdWorker;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.command.LikeCommand;
import com.whoiszxl.taowu.cqrs.command.VideoPublishCommand;
import com.whoiszxl.taowu.cqrs.query.MemberTimelineQuery;
import com.whoiszxl.taowu.cqrs.response.VideoResponse;
import com.whoiszxl.taowu.dto.VideoAuditMqDto;
import com.whoiszxl.taowu.entity.Video;
import com.whoiszxl.taowu.enums.LikeTypeEnum;
import com.whoiszxl.taowu.enums.VideoCounterStatusEnum;
import com.whoiszxl.taowu.enums.VideoStatusEnum;
import com.whoiszxl.taowu.mapper.VideoMapper;
import com.whoiszxl.taowu.member.dto.MemberDTO;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.taowu.member.feign.MemberRelationFeignClient;
import com.whoiszxl.taowu.rocketmq.RocketMQSenderService;
import com.whoiszxl.taowu.service.IVideoService;
import com.whoiszxl.taowu.starter.lock.DistributedLock;
import com.whoiszxl.taowu.starter.lock.DistributedLockFactory;
import com.whoiszxl.taowu.strategy.LikeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

    private final RocketMQSenderService rocketMQSenderService;

    private final DistributedLockFactory distributedLockFactory;

    private final MemberRelationFeignClient memberRelationFeignClient;

    private final RedisUtils redisUtils;

    /** 批量判断用户是否是热门用户的 LUA 脚本 */
    private static final String CHECK_MEMBER_HOT_LUA;

    private static final String MERGE_FEED_BOX_LUA;

    static {
        CHECK_MEMBER_HOT_LUA = "local bitmapKey = KEYS[1];" +
                                "local offsets = ARGV;" +
                                "local result = {};" +
                                "for i, offset in ipairs(offsets) do " +
                                "    if redis.call('GETBIT', bitmapKey, offset) == 1 then " +
                                "        table.insert(result, offset);" +
                                "    end;" +
                                "end;" +
                                "return result;";

        MERGE_FEED_BOX_LUA = "local destKey = '" + RedisPrefixConstants.Video.FEED_INBOX_PREFIX + "' .. KEYS[1];" +
                "for i=1, #ARGV do " +
                "    redis.call('ZUNIONSTORE', destKey, 2, destKey, '" + RedisPrefixConstants.Video.FEED_OUTBOX_PREFIX + "' .. ARGV[i]); " +
                "end;";
    }


    @Override
    public void publish(VideoPublishCommand command) {
        AppLoginMember appLoginMember = tokenHelper.getAppLoginMember();

        // 发布视频，默认为未发布状态，需要等审核通过之后才能进入发布状态
        Video video = BeanUtil.copyProperties(command, Video.class);
        video.setId(idWorker.nextId());
        video.setMemberId(appLoginMember.getId());
        video.setMemberUsername(appLoginMember.getFullName());
        video.setMemberAvatar(appLoginMember.getAvatar());
        video.setStatus(VideoStatusEnum.UNPUBLISHED.getCode());
        this.save(video);

        VideoAuditMqDto videoAuditMqDto = BeanUtil.copyProperties(video, VideoAuditMqDto.class);
        videoAuditMqDto.setIsHot(appLoginMember.getIsHot());
        // 发送审核消息，审核通过后再发送 timeline feed 流消息
        rocketMQSenderService.sendMessage(RocketMQConstant.AUDIT_VIDEO_TOPIC, JsonUtil.toJson(videoAuditMqDto));
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
     * 1. 先尝试将当前用户关注的热门用户的 outbox 都合并到当前用户的 inbox 中，也就是执行拉取操作。
     * 一般平台的关注是有上限的，所以这一步合并的操作不会太多，也不会太少，基本是在 2000-10000 的区间。
     * 所以，这一步需要将 2000-10000 个 ZSET 合并到一个 ZSET 中，所以，尽量不要频繁执行。
     * 2. 接着直接读取合并后的 ZSET，按照 SCORE 进行排序和分页，获取到关注 FEED 流的 Id 集合。
     * 3. 接着根据 ID 集合获取到 FEED 消息的具体内容，比如说视频的 URL、Desc、Cover 等信息。
     * 4. 再接着获取所有的内容发布者的信息，比如说头像、昵称等等，此处做了冗余，考虑到头像、昵称更新不是高频操作。
     * 5. 接着查看用户是否设置了过滤词，如果有则需要进行过滤，剔除用户不感兴趣的 FEED 消息。
     * 6. 最后通过计数服务获取 FEED 消息的点赞数，组装后直接返回给用户。
     *
     * 广告实现：
     * 1. 通过大数据实时分析用户标签，比如说：单机游戏，粤语流行乐
     * 2. 根据对应的标签匹配上对应的广告，将对应的广告ID推送到用户的 inbox 中。
     * @param pageQuery
     * @return
     */
    @Override
    public SetPageResponse<VideoResponse> attentionFeedList(ZSetPageQuery pageQuery) {
        Long memberId = tokenHelper.getAppMemberId();
        DistributedLock distributedLock = distributedLockFactory
                .getDistributedLock(RedisPrefixConstants.Video.FEED_UNION_LOCK_PREFIX + memberId);
        boolean locked = distributedLock.isLocked();
        // 如果当前没有锁住，则进行加锁，并执行合并 feed 流的操作
        if(!locked) {
            distributedLock.lock(5000, TimeUnit.MILLISECONDS);
            mergeHotMemberFeedBox(memberId);
        }

        // 分页读取 ZSET，需要实现滚动翻页查询
        String inboxKey = RedisPrefixConstants.Video.FEED_INBOX_PREFIX + memberId;
        Set<ZSetOperations.TypedTuple<String>> resultSet = redisUtils.zReverseRangeByScoreWithScores(inboxKey,
                0, pageQuery.getMaxScore(), pageQuery.getPage(), pageQuery.getSize());
        Long total = redisUtils.zSize(inboxKey);
        if(CollUtil.isEmpty(resultSet)) {
            return new SetPageResponse<>();
        }

        List<Long> idList = new ArrayList<>(resultSet.size());
        Iterator<ZSetOperations.TypedTuple<String>> iterator = resultSet.iterator();
        int nextPage = 1;
        long nextScore = 0L;
        while(iterator.hasNext()) {
            ZSetOperations.TypedTuple<String> item = iterator.next();
            idList.add(Long.valueOf(Objects.requireNonNull(item.getValue())));
            long score = Objects.requireNonNull(item.getScore()).longValue();
            if(nextScore == score) {
                nextPage++;
            }else {
                nextPage = 1;
                nextScore = score;
            }
        }

        // 获取到 FEED 信息的具体内容，此处简化直接通过数据库获取，这种方式性能偏差，如果 video 表做了分库分表的话，查询效率会非常低。
        // 更好的做法是将 FEED 信息存储到缓存中，通过 protocol buffers 进行序列化，减小占用空间，提升序列化性能，提升查询效率。
        List<Video> videoList = this.list(Wrappers.<Video>lambdaQuery()
                .in(Video::getId, idList)
                .orderByDesc(Video::getCreatedAt));
        SetPageResponse<VideoResponse> response = new SetPageResponse<>();
        response.setNextPage(nextPage);
        response.setNextMaxScore(nextScore);
        response.setTotal(total);

        List<VideoResponse> videoResponseList = BeanUtil.copyToList(videoList, VideoResponse.class);

        // 获取计数信息
        videoResponseList.forEach(videoResponse -> {
            Map<Object, Object> counterInfoMap = redisUtils.hGetAll(
                    RedisPrefixConstants.Counter.COUNTER_PREFIX
                            + CounterDimTypeEnum.CONTENT.getCode() + ":"
                            + videoResponse.getId());

            videoResponse.setCounterInfoMap(counterInfoMap);
        });

        response.setList(videoResponseList);

        return response;
    }

    private void mergeHotMemberFeedBox(Long memberId) {
        // 获取用户关注的热门用户，需要先从关注表中获取到关注用户列表，再去用户表中查是否是热门用户，性能偏差。
        // 可以考虑使用 bitmap 将用户的热门状态写入，那么就不需要再去用户表中查询了。10亿用户仅占用120MB内存。
        List<String> attentionIdList = memberRelationFeignClient.getMemberAttentionIdList(memberId).getData();

        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>(CHECK_MEMBER_HOT_LUA, List.class);
        redisScript.setResultType(List.class);

        Object scriptResultObj = redisUtils.execute(
                redisScript,
                CollUtil.toList(RedisPrefixConstants.Counter.COUNTER_HOT_BITMAP),
                attentionIdList.toArray(new Object[0]));
        List<Object> hotMemberIdList = (List<Object>) scriptResultObj;

        // 合并 feed 流，可以做到和上一个 LUA 脚本合并成一个
        redisUtils.execute(new DefaultRedisScript<>(MERGE_FEED_BOX_LUA),
                CollUtil.toList(memberId.toString()),
                hotMemberIdList.toArray(new Object[0]));
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
            return videoResponse;
        });
    }

    @Override
    public void pullAttentionFeed() {
        // 获取用户关注的热门用户列表


    }
}
