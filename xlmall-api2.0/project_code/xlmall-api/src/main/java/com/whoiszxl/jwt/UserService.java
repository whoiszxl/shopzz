package com.whoiszxl.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.User;

/**
 * jwt user服务
 * @author whoiszxl
 *
 */
@Component("jwtUserService")
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User getUser(String username) {
		//查询数据库是否有这个用户		
		return userMapper.selectUserByUsername(username);	
	}
}
