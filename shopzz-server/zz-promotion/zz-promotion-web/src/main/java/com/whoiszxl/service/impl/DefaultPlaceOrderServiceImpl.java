package com.whoiszxl.service.impl;
import java.math.BigDecimal;

import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.cqrs.cache.SeckillCache;
import com.whoiszxl.cqrs.cache.SeckillItemCache;
import com.whoiszxl.cqrs.cache.StockCache;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.cqrs.dto.SeckillPlaceOrderDTO;
import com.whoiszxl.dozer.DozerUtils;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.entity.SeckillItem;
import com.whoiszxl.entity.SeckillOrder;
import com.whoiszxl.enums.StatusEnum;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.service.*;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.AuthUtils;
import com.whoiszxl.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 默认下单服务接口实现
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "shopzz.placeOrderType", havingValue = "default")
public class DefaultPlaceOrderServiceImpl implements PlaceOrderService {

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private SeckillItemCachedService seckillItemCachedService;

    @Autowired
    private SeckillCachedService seckillCachedService;

    @Autowired
    private StockCacheService stockCacheService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private IdWorker idWorker;

    @Override
    public Long doPlaceOrder(Long memberId, SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        boolean allowFlag = checkSeckill(seckillOrderSubmitCommand.getSeckillId());
        AssertUtils.isTrue(allowFlag, "秒杀活动校验失败");

        allowFlag = checkSeckillItem(seckillOrderSubmitCommand.getSeckillItemId());
        AssertUtils.isTrue(allowFlag, "秒杀商品校验失败");

        //缓存中获取秒杀item，并将缓存中的库存设置上
        SeckillItemCache seckillItemCache = seckillItemCachedService.getCachedSeckillItem(seckillOrderSubmitCommand.getSeckillItemId(), null);
        putStockToItem(seckillItemCache.getSeckillItem());
        SeckillItem seckillItem = seckillItemCache.getSeckillItem();

        boolean preSubCacheFlag = false;
        try{
            //进行库存预扣减
            preSubCacheFlag = stockCacheService.subCacheStock(seckillOrderSubmitCommand.getSeckillItemId(), seckillOrderSubmitCommand.getQuantity());
            AssertUtils.isTrue(preSubCacheFlag, "库存预扣减失败");

            //进行库存实际扣减
            boolean subDbFlag = seckillItemService.subDbStock(seckillOrderSubmitCommand.getSeckillItemId(), seckillOrderSubmitCommand.getQuantity());
            AssertUtils.isTrue(subDbFlag, "库存扣减失败");

            //持久化订单
            SeckillOrder seckillOrder = buildSeckillOrder(seckillItem, seckillOrderSubmitCommand);
            boolean saveFlag = seckillOrderService.save(seckillOrder);
            if(!saveFlag) {
                ExceptionCatcher.catchValidateEx(ResponseResult.buildError("下单失败"));
            }

            //发送下单成功的领域事件


            return seckillOrder.getId();
        }catch (Exception e) {
            //如果预下单成功了，回滚缓存里的库存数量
            if(preSubCacheFlag) {
                boolean recoverFlag = stockCacheService.addCacheStock(seckillOrderSubmitCommand.getSeckillItemId(), seckillOrderSubmitCommand.getQuantity());
                if(!recoverFlag) {
                    log.error("预扣减库存恢复失败，用户ID:{}, 秒杀商品ID:{}, 秒杀数量:{}", memberId, seckillOrderSubmitCommand.getSeckillItemId(), seckillOrderSubmitCommand.getQuantity());
                }

                ExceptionCatcher.catchValidateEx(ResponseResult.buildError("下单失败"));
            }
        }

        return null;
    }

    @Override
    public void handlePlaceOrderTask(SeckillPlaceOrderDTO seckillPlaceOrderDTO) {

    }

    private SeckillOrder buildSeckillOrder(SeckillItem seckillItem, SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setMemberId(AuthUtils.getMemberId());
        seckillOrder.setSkuName(seckillItem.getSkuName());
        seckillOrder.setSkuPrice(seckillItem.getSkuPrice());
        seckillOrder.setSeckillPrice(seckillItem.getSeckillPrice());
        seckillOrder.setFinalPayAmount(seckillItem.getSeckillPrice().multiply(new BigDecimal(seckillOrderSubmitCommand.getQuantity())));
        seckillOrder.setSeckillId(seckillOrderSubmitCommand.getSeckillId());
        seckillOrder.setSeckillItemId(seckillOrderSubmitCommand.getSeckillItemId());
        seckillOrder.setBuyQuantity(seckillOrderSubmitCommand.getQuantity());
        return seckillOrder;
    }

    private void putStockToItem(SeckillItem seckillItem) {
        StockCache stockCache = stockCacheService.getAvailableStock(seckillItem.getId());

        if(stockCache != null && stockCache.isSuccess() && stockCache.getAvailableStockQuantity() != null) {
            stockCache.setAvailableStockQuantity(stockCache.getAvailableStockQuantity());
        }
    }

    private boolean checkSeckillItem(Long seckillItemId) {
        SeckillItemCache seckillItemCache = seckillItemCachedService.getCachedSeckillItem(seckillItemId, null);

        //2. 校验缓存是否需要延迟加载
        if(!seckillItemCache.isLater()) {
            log.info("从缓存中获取秒杀商品信息需要延迟加载, 秒杀商品ID: {}", seckillItemId);
            return false;
        }

        //3. 校验活动是否存在
        if(!seckillItemCache.isExist() || seckillItemCache.getSeckillItem() == null) {
            log.info("秒杀商品不存在, 秒杀商品ID: {}", seckillItemId);
            return false;
        }

        //4. 校验活动是否上线
        SeckillItem seckillItem = seckillItemCache.getSeckillItem();
        if(!seckillItem.getStatus().equals(StatusEnum.OPEN.getCode())) {
            log.info("秒杀商品未开启, 秒杀商品ID: {}", seckillItemId);
            return false;
        }

        //5. 校验活动是否在进行中
        LocalDateTime now = LocalDateTime.now();
        if(!seckillItem.getStartTime().isBefore(now) || !seckillItem.getEndTime().isAfter(now)) {
            log.info("秒杀商品未在开启时间内, 秒杀商品ID: {}", seckillItemId);
            return false;
        }

        return false;
    }

    private boolean checkSeckill(Long seckillId) {
        //1. 从缓存中拿到活动信息
        SeckillCache seckillCache = seckillCachedService.getCachedSeckill(seckillId, null);

        //2. 校验缓存是否需要延迟加载
        if(!seckillCache.isLater()) {
            log.info("从缓存中获取秒杀活动信息需要延迟加载, 秒杀活动ID: {}", seckillId);
            return false;
        }

        //3. 校验活动是否存在
        if(!seckillCache.isExist() || seckillCache.getSeckill() == null) {
            log.info("活动不存在, 秒杀活动ID: {}", seckillId);
            return false;
        }

        //4. 校验活动是否上线
        Seckill seckill = seckillCache.getSeckill();
        if(!seckill.getStatus().equals(StatusEnum.OPEN.getCode())) {
            log.info("活动未开启, 秒杀活动ID: {}", seckillId);
            return false;
        }

        //5. 校验活动是否在进行中
        LocalDateTime now = LocalDateTime.now();
        if(!seckill.getStartTime().isBefore(now) || !seckill.getEndTime().isAfter(now)) {
            log.info("活动未在开启时间内, 秒杀活动ID: {}", seckillId);
            return false;
        }

        return true;
    }
}
