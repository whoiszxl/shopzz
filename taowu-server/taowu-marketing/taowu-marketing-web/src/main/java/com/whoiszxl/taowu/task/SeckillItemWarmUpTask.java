package com.whoiszxl.taowu.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.taowu.entity.Seckill;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.service.SeckillItemService;
import com.whoiszxl.taowu.service.SeckillService;
import com.whoiszxl.taowu.service.StockCacheService;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.utils.LogFormatUtil;
import com.whoiszxl.taowu.enums.SeckillItemStatusEnum;
import com.whoiszxl.taowu.enums.WarmUpStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 秒杀商品预热任务
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SeckillItemWarmUpTask {

    private final StockCacheService stockCacheService;

    private final SeckillItemService seckillItemService;

    private final SeckillService seckillService;

    /**
     * 秒杀商品预热任务，五秒一次
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void warmUpSeckillItem() {
        log.info(LogFormatUtil.format(this, "秒杀商品预热任务开始执行", null));

        //获取DB中所有未预热的秒杀商品
        List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery()
                .eq(SeckillItem::getWarmUpStatus, WarmUpStatusEnum.NO.getCode()));

        for (SeckillItem seckillItem : seckillItemList) {
            boolean initFlag = stockCacheService.initItemStock(seckillItem.getId());
            if(!initFlag) {
                log.info(LogFormatUtil.format(this, "秒杀商品预热失败", seckillItem.getId()));
                continue;
            }

            seckillItemService.update(Wrappers.<SeckillItem>lambdaUpdate()
                    .set(SeckillItem::getWarmUpStatus, WarmUpStatusEnum.YES.getCode())
                    .set(SeckillItem::getStatus, SeckillItemStatusEnum.OPEN.getCode())
                    .eq(SeckillItem::getId, seckillItem.getId()));

            log.info(LogFormatUtil.format(this, "秒杀商品预热成功", seckillItem.getId()));
        }
        log.info(LogFormatUtil.format(this, "秒杀商品预热任务执行结束", null));
    }

    private final RedissonClient redissonClient;


    /**
     * 通过定时任务构建一个秒杀活动id的布隆过滤器
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void buildSeckillBloomFilter() {
        //通过redisson构建一个布隆过滤器
        RBloomFilter<String> seckillBloom = redissonClient.getBloomFilter(RedisPrefixConstants.Marketing.BLOOM_SECKILL_ID);

        //初始化，预计元素1000W，误差率为百分之一
        seckillBloom.tryInit(1000 * 10000,0.01);

        //将秒杀活动的主键id全部添加到布隆过滤器里面
        List<Seckill> seckillList = seckillService.list();
        for (Seckill seckill : seckillList) {
            seckillBloom.add(seckill.getId().toString());
        }
    }


    @Scheduled(cron = "*/5 * * * * ?")
    public void buildBloomFilter() {
        //通过redisson构建一个布隆过滤器
        RBloomFilter<String> testBloom = redissonClient.getBloomFilter("test_bloom");

        //初始化，预计元素100W，误差率为百分之一
        testBloom.tryInit(100 * 10000,0.01);

        //将秒杀item的主键id全部添加到布隆过滤器里面
        List<SeckillItem> seckillItemList = seckillItemService.list();
        for (SeckillItem seckillItem : seckillItemList) {
            testBloom.add(seckillItem.getId().toString());
        }
    }
}
