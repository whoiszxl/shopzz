package com.whoiszxl.taowu.consumer;

import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.constants.RocketMQConstant;
import com.whoiszxl.taowu.common.entity.ResponseResult;
import com.whoiszxl.taowu.common.enums.StatusEnum;
import com.whoiszxl.taowu.common.utils.JsonUtil;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.cqrs.dto.VideoAuditMqDto;
import com.whoiszxl.taowu.dto.KeywordFeignDto;
import com.whoiszxl.taowu.entity.Video;
import com.whoiszxl.taowu.enums.VideoStatusEnum;
import com.whoiszxl.taowu.feign.SensitiveWordFeignClient;
import com.whoiszxl.taowu.member.feign.MemberFeignClient;
import com.whoiszxl.taowu.member.feign.MemberRelationFeignClient;
import com.whoiszxl.taowu.service.IVideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 视频审核消费者
 *
 * @author whoiszxl
 * @date 2022/5/19
 */
@Slf4j
@RequiredArgsConstructor
@Component
@RocketMQMessageListener(topic = RocketMQConstant.AUDIT_VIDEO_TOPIC, consumerGroup = RocketMQConstant.TAOWU_GROUP)
public class VideoAuditConsumer implements RocketMQListener<String> {

    private final SensitiveWordFeignClient sensitiveWordFeignClient;

    private final IVideoService videoService;

    private final RedisUtils redisUtils;

    private final MemberRelationFeignClient memberRelationFeignClient;

    private static final String BATCH_ADD_INBOX_LUA;


    static {
        BATCH_ADD_INBOX_LUA = "local currentTime = redis.call('time')\n" +
                                "local timestamp = tonumber(currentTime[1])\n" +
                                "for i, memberId in ipairs(KEYS) do\n" +
                                "    redis.call('zadd', '" + RedisPrefixConstants.Video.FEED_INBOX_PREFIX + "' .. memberId, timestamp, ARGV[1])\n" +
                                "end";
    }

    @Override
    public void onMessage(String json) {
        // 视频审核，审核文字与视频
        VideoAuditMqDto videoAuditMqDto = JsonUtil.fromJson(json, VideoAuditMqDto.class);

        // 文字审核
        ResponseResult<Boolean> verifyStatus = sensitiveWordFeignClient.verifyKeyword(new KeywordFeignDto(videoAuditMqDto.getDescs()));
        if(!verifyStatus.isOk()) {
            // TODO 如果没有审核通过，则记录日志，并将结果推送给用户
            return;
        }
        // TODO 第三方视频内容鉴别

        // 审核通过，修改发布状态
        Video video = new Video();
        video.setId(videoAuditMqDto.getId());
        video.setStatus(VideoStatusEnum.PUBLISHED.getCode());
        videoService.updateById(video);

        // 视频 timeline feed 流推送
        // 1. 视频主是热门用户，则拉取视频主的活跃粉丝进行 PUSH，非活跃用户通过 PULL 的方式来获取 feed 信息
        // 2. 视频主是非热门用户，则拉取视频主的所有粉丝进行 PUSH

        // 此处实现简化一下，视频主是热门用户，则直接采取粉丝 PULL 的方式，
        // 视频主是非热门用户的话，说明粉丝少，则直接将 feed 消息 PUSH 到粉丝的 inbox


        Long memberId = videoAuditMqDto.getMemberId();
        Long videoId = videoAuditMqDto.getId();
        long timestamp = System.currentTimeMillis();

        // 添加到发件箱中
        redisUtils.zAdd(RedisPrefixConstants.Video.FEED_OUTBOX_PREFIX + memberId, String.valueOf(videoId), timestamp);
        if(StatusEnum.CLOSE.getCode().equals(videoAuditMqDto.getIsHot())) {
            // 批量添加到收件箱中
            RedisScript<Boolean> script = new DefaultRedisScript<>(BATCH_ADD_INBOX_LUA, Boolean.class);

            ResponseResult<List<String>> followerIdListResult = memberRelationFeignClient.getMemberFollowerIdList(memberId);
            List<String> followerIdList = followerIdListResult.getData();
            redisUtils.execute(script, followerIdList, videoId + "");
        }
    }
}
