package com.whoiszxl.jwt;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.User;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

/**
 * jwt user服务
 * @author whoiszxl
 *
 */
@Component("jwtUserService")
public class JwtUserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 通过用户名获取到用户的所有信息
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		//查询数据库是否有这个用户	
		return userMapper.selectUserByUsername(username);	
	}
	
	/**
	 * 通过token获取到用户实体
	 * @param token
	 * @return
	 */
	public User getUserByToken(String token) {
		String username = JWTUtil.getUsername(token);
		User user = this.getUser(username);
		user.setPassword(StringUtils.EMPTY);
		
		return user;
	}
	
	/**
	 * 获取当前登录用户的实体
	 * @param request
	 * @return
	 */
	public User getCurrentUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		User user = this.getUserByToken(token);
		return user;
	}
}
