package com.whoiszxl.task;

import com.whoiszxl.service.SeckillItemService;
import com.whoiszxl.service.StockCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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


        log.info("秒杀商品预热任务执行结束");
    }
}
