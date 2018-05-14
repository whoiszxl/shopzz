package com.whoiszxl.cron;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.RedissonManager;
import com.whoiszxl.service.OrderService;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

/**
 * 关于订单的定时任务调度
 * @author whoiszxl
 *
 */
@Component
public class OrderJobs {

	private static Logger logger = LoggerFactory.getLogger(OrderJobs.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RedissonManager redissonManager;
	
	
	/**
	 * tomcat,shutdown方式关闭的时候会执行
	 */
	@PreDestroy
	public void delLock() {
		RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
	}
	
	//@Scheduled(cron="0/10 * * * * ?")
    public void cronJob(){
        logger.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour","2"));
        orderService.closeOrder(hour);
        logger.info("关闭订单定时任务结束");
    }
	
	
	//@Scheduled(cron="0/10 * * * * ?")
    public void closeOrderByRedisShardedLockV2(){
        logger.info("RedisShardedLock关闭订单定时任务启动");
        Long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("close.order.lock.timeout","50000"));
        //将分布式锁设置进去，并加上超时时间，如果分布式锁存在就不存
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()+lockTimeout));
        //判断是否设置成功了，成功了就关闭订单，不成功就什么都不管
        if(setnxResult != null && setnxResult.intValue() == 1) {
        	//1:设置成功
        	closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }else {
        	logger.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        
        logger.info("RedisShardedLock关闭订单定时任务结束");
    }
	
	
	//@Scheduled(cron="0/10 * * * * ?")
    public void closeOrderByRedisShardedLockV3(){
        logger.info("RedisShardedLock关闭订单定时任务启动");
        Long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("close.order.lock.timeout","50000"));
        //将分布式锁设置进去，并加上超时时间，如果分布式锁存在就不存
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()+lockTimeout));
        //判断是否设置成功了，成功了就关闭订单
        if(setnxResult != null && setnxResult.intValue() == 1) {
        	//1:设置成功
        	closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }else {
        	logger.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        	//未获取到锁，继续判断，判断时间戳，看看是否可以重置并获取到锁
        	
        	//直接拿到已经设置了的lock锁
        	String lockValueStr = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        	//然后判断这个锁不能为空，并且当前时间要大于锁的有效时间
        	if(lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
        		//然后再重新getset一发锁
        		String getsetResult = RedisShardedPoolUtil.getset(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()+lockTimeout));
        		//getset获取到的老值去和之前已经存在的lock锁做对比，如果是一样的话，那就是这段时间没人做操作了，然后这个就能安全使用了
        		//或者拿到的为空，就是锁被删除掉了，也是安全的
        		if(getsetResult == null || (getsetResult != null && StringUtils.equals(lockValueStr, getsetResult))) {
        			//真正获取到锁
        			closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        		}else {
        			logger.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        		}
        	}else {
        		logger.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        	}
        }
        
        logger.info("RedisShardedLock关闭订单定时任务结束");
    }
	
	
	@Scheduled(cron="0 0/5 * * * ? ")
    public void closeOrderByRedisShardedLockV4() {
		RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
		boolean getLock = false;
		try {
			if(getLock = lock.tryLock(0, 5, TimeUnit.SECONDS)) {
				logger.info("redisson获取分布式锁：{}，ThreadName：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
				int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour","2"));
		        orderService.closeOrder(hour);
			}else {
				logger.info("redisson没有获取到分布式锁：{}，ThreadName：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
			}
			
		} catch (InterruptedException e) {
			logger.error("redisson分布式锁获取异常", e);
		}finally {
			if(!getLock) {
				return;
			}
			lock.unlock();
			logger.info("redisson分布式锁释放了");
		}
		
	}
	
	private void closeOrder(String lockName) {
		//设置有效期50秒，防止死锁
		RedisShardedPoolUtil.expire(lockName, 50);
		logger.info("获取{}，ThreadName：{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
		int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour","2"));
		orderService.closeOrder(hour);
		//在成功关闭了订单后要及时把锁清除掉
		RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
		logger.info("释放{}，ThreadName：{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
		logger.info("===========================");
	}
}
