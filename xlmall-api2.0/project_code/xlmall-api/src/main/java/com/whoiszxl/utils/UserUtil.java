package com.whoiszxl.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;

/**
 * 获取当前用户的工具类
 * @author Administrator
 *
 */
public class UserUtil {
	
	
	/**
	 * 获取当前登录的用户实体对象
	 * @param request
	 * @return
	 */
	public static User getCurrentUser(HttpServletRequest request) {
		String loginToken = CookieUtil.readLoginToken(request);
		if(StringUtils.isEmpty(loginToken)) {
			return null;
		}
		
		String userJsonStr = RedisShardedPoolUtil.get(loginToken);
		User user = JsonUtil.string2Obj(userJsonStr, User.class);
		return user;
	}
	
}
