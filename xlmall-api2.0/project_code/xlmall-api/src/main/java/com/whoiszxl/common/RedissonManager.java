package com.whoiszxl.common;

import javax.annotation.PostConstruct;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.PropertiesUtil;


/**
 * 
 * @author whoiszxl
 *
 */
@Component
public class RedissonManager {
	
	private static Logger log = LoggerFactory.getLogger(RedissonManager.class);
	
	private Config config = new Config();
	
	private Redisson redisson = null;
	
	private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
	private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
	private static String redis1Pass = PropertiesUtil.getProperty("redis1.password");
	
	
	private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
	private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));
	private static String redis2Pass = PropertiesUtil.getProperty("redis2.password");
	
	
	@PostConstruct //类似static静态代码块，初始化执行一次
	private void init() {
		try {
			config.useSingleServer().setAddress(new StringBuilder().append(redis1Ip).append(":").append(redis1Port).toString()).setPassword(redis1Pass);
			redisson = (Redisson) Redisson.create(config);
			log.info("初始化Redisson结束");
		} catch (Exception e) {
			log.info("Redisson初始化失败",e);
		}
	}
	
	public Redisson getRedisson() {
		return redisson;
	}
}
