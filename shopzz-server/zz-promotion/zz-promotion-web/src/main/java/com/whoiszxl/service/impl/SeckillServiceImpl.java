package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.DistributedLock;
import com.whoiszxl.DistributedLockFactory;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.command.SeckillOrderResultCommand;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.entity.SeckillOrder;
import com.whoiszxl.enums.promotion.SeckillOrderStatusEnum;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.SeckillMapper;
import com.whoiszxl.service.*;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PlaceOrderService placeOrderService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private StockCacheService stockCacheService;

    @Override
    @Transactional
    public String orderSubmit(SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        //0. 获取当前登录用户
        Long memberId = AuthUtils.getMemberId();
        //1. 获取用户分布式锁
        String lockKey = RedisKeyPrefixConstants.SECKILL_LOCK_ORDER_SUBMIT + memberId;
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
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("加锁失败"));
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
        Long memberId = AuthUtils.getMemberId();
        log.info("orderCancel|取消秒杀订单|{},{}", memberId, orderId);
        if(orderId == null) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("订单号无效"));
        }

        SeckillOrder seckillOrder = seckillOrderService.getById(orderId);
        if(seckillOrder == null || seckillOrder.getMemberId().equals(memberId)) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("订单号无效"));
        }

        if(seckillOrder.getStatus().equals(SeckillOrderStatusEnum.CANCEL.getCode())) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("订单已取消"));
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
