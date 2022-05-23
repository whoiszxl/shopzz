package com.whoiszxl.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.enums.promotion.SeckillItemStatusEnum;
import com.whoiszxl.enums.promotion.WarmUpStatusEnum;
import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.service.StockCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//@Component
public class SeckillItemWarmUpTask {

    @Autowired
    private StockCacheService stockCacheService;

    @Autowired
    private SeckillItemService seckillItemService;

    /**
     * 秒杀商品预热任务，五秒一次
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void warmUpSeckillItem() {
        log.info("warmUpSeckillItem|秒杀商品预热任务开始执行");

        //获取DB中所有未预热的秒杀商品
        List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery()
                .eq(SeckillItem::getWarmUpStatus, WarmUpStatusEnum.NO.getCode()));

        for (SeckillItem seckillItem : seckillItemList) {
            boolean initFlag = stockCacheService.initItemStock(seckillItem.getId());
            if(!initFlag) {
                log.info("warmUpSeckillItem|秒杀商品预热失败|{}", seckillItem.getId());
                continue;
            }

            seckillItemService.update(Wrappers.<SeckillItem>lambdaUpdate()
                    .set(SeckillItem::getWarmUpStatus, WarmUpStatusEnum.YES.getCode())
                    .set(SeckillItem::getStatus, SeckillItemStatusEnum.OPEN.getCode())
                    .eq(SeckillItem::getId, seckillItem.getId()));

            log.info("warmUpSeckillItem|秒杀商品预热成功|{}", seckillItem.getId());
        }

        log.info("warmUpSeckillItem|秒杀商品预热任务执行结束");
    }
}
