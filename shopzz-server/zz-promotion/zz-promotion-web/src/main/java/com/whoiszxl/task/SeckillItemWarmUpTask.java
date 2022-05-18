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
 * @author zhouxiaolong
 * @date 2022/5/18
 */
@Slf4j
@Component
public class SeckillItemWarmUpTask {

    @Autowired
    private StockCacheService stockCacheService;

    @Autowired
    private SeckillItemService seckillItemService;

    /**
     * 秒杀商品预热任务，五秒一次
     * TODO 修改为xxl-job调用
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void warmUpSeckillItem() {
        log.info("秒杀商品预热任务开始执行");

        //获取DB中所有未预热的秒杀商品
        List<SeckillItem> seckillItemList = seckillItemService.list(Wrappers.<SeckillItem>lambdaQuery()
                .eq(SeckillItem::getWarmUpStatus, WarmUpStatusEnum.NO.getCode()));

        for (SeckillItem seckillItem : seckillItemList) {
            boolean initFlag = stockCacheService.initItemStock(seckillItem.getId());
            if(!initFlag) {
                log.info("秒杀商品预热失败，seckill item id:{}", seckillItem.getId());
                continue;
            }

            seckillItem.setWarmUpStatus(WarmUpStatusEnum.YES.getCode());
            seckillItem.setStatus(SeckillItemStatusEnum.OPEN.getCode());
            seckillItemService.updateById(seckillItem);

            log.info("秒杀商品预热成功，seckill item id:{}", seckillItem.getId());
        }

        log.info("秒杀商品预热任务执行结束");
    }
}
