package com.whoiszxl.taowu.events;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.enums.CounterCalculationRuleEnum;
import com.whoiszxl.taowu.common.enums.CounterDimTypeEnum;
import com.whoiszxl.taowu.common.enums.CounterObjectTypeEnum;
import com.whoiszxl.taowu.common.enums.StatusEnum;
import com.whoiszxl.taowu.common.utils.RedisUtils;
import com.whoiszxl.taowu.dto.CountFeignCommand;
import com.whoiszxl.taowu.entity.Member;
import com.whoiszxl.taowu.feign.CounterFeignClient;
import com.whoiszxl.taowu.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 关注计数监听器
 * @author whoiszxl
 */
@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class AttentionCounterListener implements ApplicationListener<AttentionEvent> {

    private final CounterFeignClient counterFeignClient;

    private final RedisUtils redisUtils;

    private final IMemberService memberService;

    @Override
    public void onApplicationEvent(AttentionEvent event) {
        log.info("AttentionEvent|关注事件统计计数|{}", event.getAttentionMemberId());

        // 调用远程服务更新计数
        CountFeignCommand followCommand =  new CountFeignCommand();
        followCommand.setDimType(CounterDimTypeEnum.USER.getCode());
        followCommand.setRegulation(CounterCalculationRuleEnum.INCREASE.getCode());
        followCommand.setObjType(CounterObjectTypeEnum.FOLLOW_COUNT.getCode());
        followCommand.setObjId(event.getCurrentMemberId());
        counterFeignClient.countNumber(followCommand);

        CountFeignCommand fansCommand = new CountFeignCommand();
        fansCommand.setDimType(CounterDimTypeEnum.USER.getCode());
        fansCommand.setRegulation(CounterCalculationRuleEnum.INCREASE.getCode());
        fansCommand.setObjType(CounterObjectTypeEnum.FANS_COUNT.getCode());
        fansCommand.setObjId(event.getAttentionMemberId());
        counterFeignClient.countNumber(fansCommand);

        // 计算热点用户
        String redisKey = RedisPrefixConstants.Counter.COUNTER_PREFIX
                + CounterDimTypeEnum.USER.getCode() + ":"
                + event.getAttentionMemberId();
        Object numObj = redisUtils.hGet(redisKey, CounterObjectTypeEnum.FANS_COUNT.getCode().toString());
        Long fansCount = Long.parseLong(numObj.toString());

        // 超过 1万粉丝的则算作热门用户，将其保存到 Redis 的 Bitmap 和 更新 isHot 字段
        if(fansCount > 10) {
            redisUtils.setBit(RedisPrefixConstants.Counter.COUNTER_HOT_BITMAP, event.getAttentionMemberId(), true);
            memberService.update(Wrappers.<Member>lambdaUpdate()
                    .eq(Member::getId, event.getAttentionMemberId())
                    .set(Member::getIsHot, StatusEnum.OPEN.getCode()));
        }

    }
}