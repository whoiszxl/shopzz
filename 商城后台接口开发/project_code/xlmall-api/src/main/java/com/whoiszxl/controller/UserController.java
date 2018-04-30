package com.whoiszxl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.entity.User;
import com.whoiszxl.service.UserService;

/**
 * 用户控制器
 * @author whoiszxl
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
	
}
