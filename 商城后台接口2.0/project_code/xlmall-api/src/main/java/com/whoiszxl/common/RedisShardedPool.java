package com.whoiszxl.common;

import java.util.ArrayList;
import java.util.List;

import com.whoiszxl.utils.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class RedisShardedPool {
	/**
	 * jedis连接池
	 */
	private static ShardedJedisPool pool;
	/**
	 * 最大连接数
	 */
	private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
	
	/**
	 * 在jedisPool中最大的idle状态（空闲）的jedis实例的个数
	 */
	private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));
	
	/**
	 * 在jedisPool中最小的idle状态（空闲）的jedis实例的个数
	 */
	private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","5"));

	/**
	 * 在borrow一个jedis实例时，是否要进行验证操作，
	 * 如果赋值为true，则得到的jedis实例是必定可以使用的
	 */
	private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
	
	/**
	 * 在return一个jedis实例时，是否要进行验证操作，
	 * 如果赋值为true，则返回的jedis实例是必定可以使用的
	 */
	private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));
	
	
	private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
	private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
	private static String redis1Pass = PropertiesUtil.getProperty("redis1.password");
	
	
	private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
	private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));
	private static String redis2Pass = PropertiesUtil.getProperty("redis2.password");
	
	/**
	 * 初始化连接池
	 */
	private static void initPool() {
		//创建连接池配置文件
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		
		config.setBlockWhenExhausted(true);//连接耗尽时是否阻塞，false抛出异常，默认true
		
		//创建两个redis源的配置文件，并加入list中
		JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port);
		info1.setPassword(redis1Pass);
		JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port);
		info2.setPassword(redis2Pass);
		
		List<JedisShardInfo> jedisShardInfoList = new ArrayList<>(2);
		jedisShardInfoList.add(info1);
		jedisShardInfoList.add(info2);
		
		//通过配置好的两个源，创建一个一致性hash算法的redis连接池
		pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
		
	}
	
	static {
		System.out.println("初始化Redis连接池了哟");
		initPool();
	}
	
	public static ShardedJedis getJedis() {
		return pool.getResource();
	}
	
	public static void returnResource(ShardedJedis jedis) {
		if(jedis != null) {
			pool.returnResource(jedis);
		}
	}
	
	public static void returnBrokenResource(ShardedJedis jedis) {
		if(jedis != null) {
			pool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		ShardedJedis jedis = pool.getResource();
		//将10个键值对打到redis上，两台redis会尽量做平均处理
		for(int i=0;i<10;i++) {
			jedis.set("key"+i, "value"+i);
		}
		
		pool.destroy();
		System.out.println("end");
	}
}
