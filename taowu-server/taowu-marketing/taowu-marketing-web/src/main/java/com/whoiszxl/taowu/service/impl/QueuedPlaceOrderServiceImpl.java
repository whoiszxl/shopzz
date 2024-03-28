package com.whoiszxl.taowu.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.whoiszxl.taowu.common.constants.RedisPrefixConstants;
import com.whoiszxl.taowu.common.constants.RocketMQConstant;
import com.whoiszxl.taowu.common.enums.StatusEnum;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import com.whoiszxl.taowu.common.token.TokenHelper;
import com.whoiszxl.taowu.common.utils.*;
import com.whoiszxl.taowu.cqrs.cache.SeckillCache;
import com.whoiszxl.taowu.cqrs.cache.SeckillItemCache;
import com.whoiszxl.taowu.cqrs.cache.StockCache;
import com.whoiszxl.taowu.cqrs.command.SeckillOrderResultCommand;
import com.whoiszxl.taowu.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.taowu.cqrs.dto.SeckillPlaceOrderDTO;
import com.whoiszxl.taowu.entity.Seckill;
import com.whoiszxl.taowu.entity.SeckillItem;
import com.whoiszxl.taowu.entity.SeckillOrder;
import com.whoiszxl.taowu.enums.SeckillOrderStatusEnum;
import com.whoiszxl.taowu.mapper.SeckillMapper;
import com.whoiszxl.taowu.rocketmq.RocketMQSenderService;
import com.whoiszxl.taowu.service.*;
import com.whoiszxl.taowu.starter.lock.DistributedLock;
import com.whoiszxl.taowu.starter.lock.DistributedLockFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 队列下单服务接口实现
 *
 * @author whoiszxl
 * @date 2022/5/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "taowu.placeOrderType", havingValue = "queued")
public class QueuedPlaceOrderServiceImpl implements PlaceOrderService {

    private final SeckillItemService seckillItemService;

    private final SeckillItemCachedService seckillItemCachedService;

    @Lazy
    private final SeckillCachedService seckillCachedService;

    private final SeckillMapper seckillMapper;

    private final StockCacheService stockCacheService;

    private final SeckillOrderService seckillOrderService;

    private final IdWorker idWorker;

    private final RedisUtils redisUtils;

    private final DistributedLockFactory distributedLockFactory;

    private final RocketMQSenderService rocketMQSenderService;

    private final TokenHelper tokenHelper;

    private final static Cache<Long, Integer> availableOrderTokenLocalCache
            = CacheBuilder.newBuilder()
                .initialCapacity(20) //初始化容量为20
                .concurrencyLevel(5) //并发写缓存的线程数
                .expireAfterWrite(20, TimeUnit.MILLISECONDS).build(); ////写缓存后20秒过期


    private static final String TAKE_ORDER_TOKEN_LUA;

    private static final String RECOVER_ORDER_TOKEN_LUA;

    static {
        //获取可用token数量，等于0返回-1，如果大于0则减1操作
        // KEYS[1] availableTokensCount 150 token
        TAKE_ORDER_TOKEN_LUA = "if (redis.call('exists', KEYS[1]) == 1) then" +
                "    local availableTokensCount = tonumber(redis.call('get', KEYS[1]));" + //availableTokensCount 150
                "    if (availableTokensCount == 0) then" +
                "        return -1;" +
                "    end;" +
                "    if (availableTokensCount > 0) then" +
                "        redis.call('incrby', KEYS[1], -1);" + //-1操作
                "        return 1;" +
                "    end;" +
                "end;" +
                "return -100;";

        //直接+1操作
        RECOVER_ORDER_TOKEN_LUA = "if (redis.call('exists', KEYS[1]) == 1) then" +
                "   redis.call('incrby', KEYS[1], 1);" +
                "   return 1;" +
                "end;" +
                "return -100;";
    }


    @Override
    public String doPlaceOrder(Long memberId, SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
        boolean allowFlag = checkSeckill(seckillOrderSubmitCommand.getSeckillId());
        AssertUtils.isTrue(allowFlag, "秒杀活动校验失败");

        allowFlag = checkSeckillItem(seckillOrderSubmitCommand.getSeckillItemId());
        AssertUtils.isTrue(allowFlag, "秒杀商品校验失败");

        //生成下单任务ID,防止重复下单，在MQ消费者里处理完成后才将taskKey删除
        String taskKey = DigestUtils.md5DigestAsHex((String.valueOf(memberId) + seckillOrderSubmitCommand.getSeckillItemId()).getBytes());
        if(redisUtils.get(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + taskKey) == "0") {
            ExceptionCatcher.catchServiceEx("不能重复下单");
        }

        //校验当前可用的秒杀订单令牌是否还存在
        Integer availableOrderToken = getAvailableOrderToken(seckillOrderSubmitCommand.getSeckillItemId());
        if(availableOrderToken == null || availableOrderToken == 0) {
            ExceptionCatcher.catchServiceEx("没有库存啦");
        }

        //获取令牌，或者恢复令牌 150 - 1  149 - 1    -> 0
        if(!takeOrRecoverToken(seckillOrderSubmitCommand.getSeckillItemId(), TAKE_ORDER_TOKEN_LUA)) {
            log.info("doPlaceOrder|获取下单令牌失败|{},{}", memberId, seckillOrderSubmitCommand.getSeckillItemId());
            ExceptionCatcher.catchServiceEx("没有库存啦");
        }

        //发送到MQ
        SeckillPlaceOrderDTO seckillPlaceOrderDTO = new SeckillPlaceOrderDTO();
        seckillPlaceOrderDTO.setMemberId(memberId);
        seckillPlaceOrderDTO.setSeckillId(seckillOrderSubmitCommand.getSeckillId());
        seckillPlaceOrderDTO.setSeckillItemId(seckillOrderSubmitCommand.getSeckillItemId());
        seckillPlaceOrderDTO.setQuantity(seckillOrderSubmitCommand.getQuantity());
        seckillPlaceOrderDTO.setTaskKey(taskKey);
        rocketMQSenderService.sendMessage(RocketMQConstant.SECKILL_PLACE_ORDER_TOPIC, JsonUtil.toJson(seckillPlaceOrderDTO));

        redisUtils.setEx(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + taskKey, "0", 24, TimeUnit.HOURS);
        log.info("doPlaceOrder|下单任务提交到MQ成功|{},{}", memberId, seckillOrderSubmitCommand.getSeckillItemId());
        return taskKey;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePlaceOrderTask(SeckillPlaceOrderDTO seckillPlaceOrderDTO) {
        try{
            //再次校验秒杀活动与秒杀商品的有效性
            boolean allowFlag = checkSeckill(seckillPlaceOrderDTO.getSeckillId());
            AssertUtils.isTrue(allowFlag, "秒杀活动校验失败");

            allowFlag = checkSeckillItem(seckillPlaceOrderDTO.getSeckillItemId());
            AssertUtils.isTrue(allowFlag, "秒杀商品校验失败");

            //缓存中获取秒杀item，并将缓存中的库存设置上
            SeckillItem seckillItem = seckillItemService.getById(seckillPlaceOrderDTO.getSeckillItemId());

            //进行库存实际扣减，因为通过token已经拦截了一次，可以直接进行数据库扣减，而不用走缓存扣减一次了
            boolean subDbFlag = seckillItemService.subDbStock(seckillPlaceOrderDTO.getSeckillItemId(), seckillPlaceOrderDTO.getQuantity());
            AssertUtils.isTrue(subDbFlag, "库存扣减失败");

            //订单持久化
            SeckillOrder seckillOrder = buildSeckillOrder(seckillItem, seckillPlaceOrderDTO, seckillPlaceOrderDTO.getMemberId());
            boolean saveFlag = seckillOrderService.save(seckillOrder);
            if(!saveFlag) {
                ExceptionCatcher.catchServiceEx("下单失败");
            }

            //如果taskKey的值为0，则说明还没执行完成，则设置为1
            Object taskValueObj = redisUtils.getObj(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + seckillPlaceOrderDTO.getTaskKey());
            if("0".equals(taskValueObj)) {
                redisUtils.set(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + seckillPlaceOrderDTO.getTaskKey(), "1");
            }

            //将订单ID设置到Redis中
            redisUtils.setEx(
                    RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ_ORDER_ID + seckillPlaceOrderDTO.getTaskKey(),
                    seckillOrder.getId().toString(),
                    24, TimeUnit.HOURS);

            log.info("handlePlaceOrderTask|MQ消费秒杀订单任务处理完成|{}", seckillPlaceOrderDTO);
        }catch (Exception e) {
            //发生异常，设置为-1
            Object taskValueObj = redisUtils.getObj(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + seckillPlaceOrderDTO.getTaskKey());
            if(taskValueObj != null && "0".equals(taskValueObj)) {
                redisUtils.set(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + seckillPlaceOrderDTO.getTaskKey(), "-1");
            }
            log.info("handlePlaceOrderTask|MQ消费秒杀订单任务处理发生异常|{}", seckillPlaceOrderDTO, e);
            ExceptionCatcher.catchServiceEx("下单失败");

        }
    }

    /**
     *
     * @param seckillItemId
     * @param takeOrderTokenLua
     * @return
     */
    private boolean takeOrRecoverToken(Long seckillItemId, String takeOrderTokenLua) {
        List<String> keys = new ArrayList<>();

        // TOKEN_SECKILL_PLACE_ORDER 150
        keys.add(RedisPrefixConstants.Marketing.TOKEN_SECKILL_PLACE_ORDER + seckillItemId);
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(takeOrderTokenLua, Long.class);

        for (int i = 0; i < 5; i++) {
            Object resultObj = redisUtils.execute(redisScript, keys);
            if(resultObj == null) {
                return false;
            }

            //如果没有拿到下单令牌，则刷新可用令牌，最多重试5次
            if((long)resultObj == -100) {
                refreshLatestAvailableToken(seckillItemId);
                continue;
            }

            return (long)resultObj == 1L;
        }
        return false;
    }


    /**
     * 从本地缓存中获取订单令牌，没有获取到就刷新本地可用令牌
     * @param seckillItemId
     * @return
     */
    private Integer getAvailableOrderToken(Long seckillItemId) {
        //1. 从本地缓存获取订单令牌，如果获取到了直接返回  orderToken = 100 * 1.5 = 150
        Integer orderToken = availableOrderTokenLocalCache.getIfPresent(seckillItemId);
        if(orderToken != null) {
            return orderToken;
        }

        //2. 本地没有获取到，则从Redis缓存获取
        return refreshLocalAvailableToken(seckillItemId);
    }

    /**
     * 刷新本地可用令牌，从redis缓存中获取，如果redis中不存在，则从库存服务里获取
     * @param seckillItemId
     * @return
     */
    private Integer refreshLocalAvailableToken(Long seckillItemId) {
        //1. 这里重新在本地缓存里获取一次，如果有其他线程加载到了本地缓存可以直接返回
        Integer orderToken = availableOrderTokenLocalCache.getIfPresent(seckillItemId);
        if(orderToken != null) {
            return orderToken;
        }

        //2. 从Redis中获取，如果Redis中获取到了，则回写到本地缓存，并返回
        String orderTokenKey = RedisPrefixConstants.Marketing.TOKEN_SECKILL_PLACE_ORDER + seckillItemId;
        Object tokenObj = redisUtils.getObj(orderTokenKey);
        if(tokenObj != null) {
            //tokenValue = 150
            int tokenValue = Integer.parseInt(tokenObj.toString());
            availableOrderTokenLocalCache.put(seckillItemId, tokenValue);
            return tokenValue;
        }

        //3. Redis中没有获取到，则从MySQL数据库中加载
        return refreshLatestAvailableToken(seckillItemId);
    }

    /**
     * 从库存里获取可用token数量
     * token数量默认是库存的1.5倍，适量放行一些请求进来争抢资源
     * @param seckillItemId
     * @return
     */
    private Integer refreshLatestAvailableToken(Long seckillItemId) {
        //1. 从库存服务加载需要使用分布式锁，避免多个服务同时更新产生并发问题
        DistributedLock distributedLock = distributedLockFactory.getDistributedLock(RedisPrefixConstants.Marketing.LOCK_SECKILL_REFRESH_PLACE_ORDER_TOKEN + seckillItemId);

        try {
            //2. 加锁，等待500毫秒，释放时间为1000毫秒
            boolean lockFlag = distributedLock.tryLock(500, 1000, TimeUnit.MILLISECONDS);
            if(!lockFlag) {
                return null;
            }

            //3. 这里就使用库存服务，从库存表里面拿到库存数量。也是通过多级缓存的形式。库存会通过定时任务预热的方式进行初始化。（warmUpSeckillItem）
            // stockCache = 100;
            StockCache stockCache = stockCacheService.getAvailableStock(seckillItemId);
            if(stockCache != null && stockCache.isSuccess() && stockCache.getAvailableStockQuantity() != null) {
                //4. 拿到库存之后，我们会把可用库存数量乘以1.5。如果库存100，那么我们这里会初始化150个令牌。
                Integer latestAvailableOrderToken = (int) Math.ceil(stockCache.getAvailableStockQuantity() * 1.5);
                redisUtils.setEx(RedisPrefixConstants.Marketing.TOKEN_SECKILL_PLACE_ORDER + seckillItemId, latestAvailableOrderToken.toString(), 24, TimeUnit.HOURS);
                availableOrderTokenLocalCache.put(seckillItemId, latestAvailableOrderToken);
                return latestAvailableOrderToken;
            }

        }catch (Exception e) {
            log.error(LogFormatUtil.format(this, "刷新最新可用令牌失败", seckillItemId), e);
        }finally {
            distributedLock.unlock();
        }
        return null;
    }

    private SeckillOrder buildSeckillOrder(SeckillItem seckillItem, SeckillPlaceOrderDTO seckillPlaceOrderDTO, Long memberId) {
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setMemberId(memberId);
        seckillOrder.setSkuName(seckillItem.getSkuName());
        seckillOrder.setSkuPrice(seckillItem.getSkuPrice());
        seckillOrder.setSeckillPrice(seckillItem.getSeckillPrice());
        seckillOrder.setFinalPayAmount(seckillItem.getSeckillPrice().multiply(new BigDecimal(seckillPlaceOrderDTO.getQuantity())));
        seckillOrder.setSeckillId(seckillPlaceOrderDTO.getSeckillId());
        seckillOrder.setSeckillItemId(seckillPlaceOrderDTO.getSeckillItemId());
        seckillOrder.setBuyQuantity(seckillPlaceOrderDTO.getQuantity());
        seckillOrder.setStatus(SeckillOrderStatusEnum.NOT_PAY.getCode());
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
        if(seckillItemCache.isLater()) {
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

        return true;
    }

    private boolean checkSeckill(Long seckillId) {
        //1. 从缓存中拿到活动信息
        SeckillCache seckillCache = seckillCachedService.getCachedSeckill(seckillId, null);

        //2. 校验缓存是否需要延迟加载
        if(seckillCache.isLater()) {
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


    @Override
    public Long getOrderResult(SeckillOrderResultCommand seckillOrderResultCommand) {
        //1. 将用户ID和秒杀itemId进行md5计算
        String taskKey = DigestUtils.md5DigestAsHex(
                (String.valueOf(tokenHelper.getAppMemberId()) + seckillOrderResultCommand.getSeckillItemId()).getBytes());

        //2. 判断传过来的异步taskKey和当前计算出来的是否一致
        if(!seckillOrderResultCommand.getTaskId().equals(taskKey)) {
            ExceptionCatcher.catchServiceEx("下单错误");
        }

        //3. 通过taskKey获取到Redis中的TASK_SECKILL_PLACE_ORDER_MQ
        // 如果此值未被设置为1，则说明异步消费未成功
        Object taskValueObj = redisUtils.getObj(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ + taskKey);
        AssertUtils.isTrue(taskValueObj != null, "下单错误");
        AssertUtils.isTrue("1".equals(taskValueObj), "下单错误");

        //4. 通过taskKey获取到TASK_SECKILL_PLACE_ORDER_MQ_ORDER_ID
        //这个值在异步操作中会被置为订单的主键ID
        Object orderIdObj = redisUtils.getObj(RedisPrefixConstants.Marketing.TASK_SECKILL_PLACE_ORDER_MQ_ORDER_ID + taskKey);
        AssertUtils.isTrue(orderIdObj != null, "下单错误");
        return Long.parseLong(orderIdObj.toString());
    }
}
