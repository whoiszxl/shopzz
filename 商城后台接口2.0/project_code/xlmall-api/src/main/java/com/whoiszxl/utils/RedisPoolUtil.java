package com.whoiszxl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whoiszxl.common.RedisPool;

import redis.clients.jedis.Jedis;

/**
 * redis工具集
 * @author whoiszxl
 *
 */
public class RedisPoolUtil {
	
	private static Logger log = LoggerFactory.getLogger(RedisPoolUtil.class);
	
	/**
	 * 重新设置key的有效期
	 * @param key 键
	 * @param exTime 有效时间（秒）
	 * @return 还剩多少时间
	 */
	public static Long expire(String key, int exTime) {
		Jedis jedis = null;
		Long result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.expire(key, exTime);
			RedisPool.returnResource(jedis);
		} catch (Exception e) {
			log.error("expire key:{} time:{} error", key, exTime, e);
			return result;
		}
		return result;
	}
	
	/**
	 * 设置倒计时失效的键值
	 * @param key 键
	 * @param value 值
	 * @param exTime 秒数
	 * @return 是否设置成功
	 */
	public static String setEx(String key, String value, int exTime) {
		Jedis jedis = null;
		String result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.setex(key, exTime, value);
			RedisPool.returnResource(jedis);
		} catch (Exception e) {
			log.error("setex key:{} time:{} value:{} error", key, exTime, value, e);
			return result;
		}
		return result;
	}
	
	/**
	 * 设置redis值
	 * @param key 键
	 * @param value 值
	 * @return 是否设置成功
	 */
	public static String set(String key, String value) {
		Jedis jedis = null;
		String result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.set(key, value);
			RedisPool.returnResource(jedis);
		} catch (Exception e) {
			log.error("set key:{} value:{} error", key, value, e);
			return result;
		}
		return result;
	}
	
	/**
	 * 获取redis值
	 * @param key 键
	 * @return 值
	 */
	public static String get(String key) {
		Jedis jedis = null;
		String result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.get(key);
			RedisPool.returnResource(jedis);
		} catch (Exception e) {
			log.error("get key:{} error", key, e);
			return result;
		}
		return result;
	}
	
	/**
	 * 删除一个键值对
	 * @param key 键
	 * @return 是否成功
	 */
	public static Long del(String key) {
		Jedis jedis = null;
		Long result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.del(key);
			RedisPool.returnResource(jedis);
		} catch (Exception e) {
			log.error("del key:{} error", key, e);
			return result;
		}
		return result;
	}
	
}
