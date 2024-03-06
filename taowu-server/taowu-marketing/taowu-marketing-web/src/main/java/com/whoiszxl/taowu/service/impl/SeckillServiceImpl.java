package com.whoiszxl.taowu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.AssertUtils;
import com.whoiszxl.taowu.cqrs.command.SeckillOrderResultCommand;
import com.whoiszxl.taowu.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.taowu.entity.Seckill;
import com.whoiszxl.taowu.entity.SeckillOrder;
import com.whoiszxl.taowu.enums.SeckillOrderStatusEnum;
import com.whoiszxl.taowu.mapper.SeckillMapper;
import com.whoiszxl.taowu.service.*;
import com.whoiszxl.taowu.starter.lock.DistributedLock;
import com.whoiszxl.taowu.starter.lock.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 秒杀表 服务实现类
 * </p>
 *
 * @author whoiszxl
 * @since 2022-04-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {

    private final DistributedLockFactory distributedLockFactory;

    private final SecurityService securityService;

    private final PlaceOrderService placeOrderService;

    private final SeckillOrderService seckillOrderService;

    private final SeckillItemService seckillItemService;

    private final StockCacheService stockCacheService;

    private final TokenHelper tokenHelper;

    @Override
    @Transactional
    public String orderSubmit(SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        //0. 获取当前登录用户
        Long memberId = tokenHelper.getAppMemberId();
        //1. 获取用户分布式锁
        String lockKey = RedisPrefixConstants.Marketing.SECKILL_LOCK_ORDER_SUBMIT + memberId;
        DistributedLock lock = distributedLockFactory.getDistributedLock(lockKey);

        try {
            //2. 尝试进行加锁，等待3秒，加锁成功后5秒释放
            boolean isLockSuccess = lock.tryLock(3, 5, TimeUnit.SECONDS);
            AssertUtils.isTrue(isLockSuccess, "加锁失败");

            //3. 对用户进行风控检查
            boolean checkFlag = securityService.inspectRisk(memberId);
            AssertUtils.isTrue(checkFlag, "风控检测未通过");

            //4. 下单
            String taskKey = placeOrderService.doPlaceOrder(memberId, seckillOrderSubmitCommand);
            AssertUtils.isNotNull(taskKey, "下单失败");

            return taskKey;
        } catch (InterruptedException e) {
            ExceptionCatcher.catchServiceEx("加锁失败");
            return null;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Long orderResult(SeckillOrderResultCommand seckillOrderResultCommand) {
        return placeOrderService.getOrderResult(seckillOrderResultCommand);
    }

    @Override
    @Transactional
    public boolean orderCancel(Long orderId) {
        Long memberId = tokenHelper.getAppMemberId();
        log.info("orderCancel|取消秒杀订单|{},{}", memberId, orderId);
        if(orderId == null) {
            ExceptionCatcher.catchServiceEx("订单号无效");
        }

        SeckillOrder seckillOrder = seckillOrderService.getById(orderId);
        if(seckillOrder == null || seckillOrder.getMemberId().equals(memberId)) {
            ExceptionCatcher.catchServiceEx("订单号无效");
        }

        if(seckillOrder.getStatus().equals(SeckillOrderStatusEnum.CANCEL.getCode())) {
            ExceptionCatcher.catchServiceEx("订单已取消");
        }

        boolean cancelFlag = seckillOrderService.orderCancel(orderId);
        AssertUtils.isTrue(cancelFlag, "订单取消失败");

        //实际库存与缓存库存恢复
        boolean dbStockRecoverFlag = seckillItemService.addDbStock(seckillOrder.getSeckillItemId(), seckillOrder.getBuyQuantity());
        AssertUtils.isTrue(dbStockRecoverFlag, "实际库存恢复失败");
        boolean cacheStockRecoverFlag = stockCacheService.addCacheStock(seckillOrder.getSeckillItemId(), seckillOrder.getBuyQuantity());
        AssertUtils.isTrue(cacheStockRecoverFlag, "缓存库存恢复失败");

        log.info("orderCancel|秒杀订单取消成功|{},{}", memberId, orderId);
        return true;
    }
}
