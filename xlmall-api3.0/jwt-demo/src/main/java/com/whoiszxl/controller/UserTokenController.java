package com.whoiszxl.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.User;

@RestController
@RequestMapping("/token/")
public class UserTokenController {

	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/test")
	public User test() {
		return userMapper. selectByPrimaryKey(1);
		//return new User(1, "学友", "123456", "whoiszxl@gmail.com", "17688900411", "你是谁", "张学友", 1, new Date(), new Date());
	}
	
}
