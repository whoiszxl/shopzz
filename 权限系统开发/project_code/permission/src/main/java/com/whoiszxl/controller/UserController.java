package com.whoiszxl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.dao.SysUserMapper;
import com.whoiszxl.model.SysUser;

@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@GetMapping("/user")
	public SysUser getUser() {
		logger.error("UserController.getUser()");
		logger.info(sysUserMapper.selectByPrimaryKey(1).toString());
		return sysUserMapper.selectByPrimaryKey(1);
	}
}
