package com.whoiszxl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whoiszxl.DistributedLock;
import com.whoiszxl.DistributedLockFactory;
import com.whoiszxl.bean.ResponseResult;
import com.whoiszxl.constants.RedisKeyPrefixConstants;
import com.whoiszxl.cqrs.cache.SeckillItemCache;
import com.whoiszxl.cqrs.command.SeckillOrderSubmitCommand;
import com.whoiszxl.entity.Seckill;
import com.whoiszxl.exception.ExceptionCatcher;
import com.whoiszxl.mapper.SeckillMapper;
import com.whoiszxl.service.*;
import com.whoiszxl.utils.AssertUtils;
import com.whoiszxl.utils.AuthUtils;
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
@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {

    @Autowired
    private SeckillItemService seckillItemService;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private SeckillItemCachedService seckillItemCachedService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PlaceOrderService placeOrderService;

    @Override
    @Transactional
    public Long orderSubmit(SeckillOrderSubmitCommand seckillOrderSubmitCommand) {
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
            Long orderId = placeOrderService.doPlaceOrder(memberId, seckillOrderSubmitCommand);
            AssertUtils.isNotNull(orderId, "下单失败");

            return orderId;
        } catch (InterruptedException e) {
            ExceptionCatcher.catchValidateEx(ResponseResult.buildError("加锁失败"));
            return null;
        }finally {
            lock.unlock();
        }
    }

}
