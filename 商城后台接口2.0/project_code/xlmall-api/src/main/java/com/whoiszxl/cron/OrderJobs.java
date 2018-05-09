package com.whoiszxl.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.whoiszxl.service.OrderService;
import com.whoiszxl.utils.PropertiesUtil;

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
	
	@Scheduled(cron="0/10 * * * * ?")
    public void cronJob(){
        logger.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.hour","2"));
        orderService.closeOrder(hour);
        logger.info("关闭订单定时任务结束");
    }
	
}
