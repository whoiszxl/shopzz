package com.whoiszxl.common;
/**
 * 
 * @author whoiszxl
 *
 */

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class TokenCache {
	
	private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
	
	public static final String TOKEN_PREFIX = "token_";
	
	private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
			.build(new CacheLoader<String, String>() {
				//默认的数据加载实现,当调用get没有值的时候默认用这个方法加载.
				@Override
				public String load(String key) throws Exception {
					return "null";
				}
			});
	
	public static void setKey(String key,String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache get error",e);
        }
        return null;
    } 
}
