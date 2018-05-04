package com.whoiszxl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.ApplicationContextHelper;
import com.whoiszxl.dao.SysUserMapper;
import com.whoiszxl.exception.PermissionException;
import com.whoiszxl.model.SysUser;

@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@GetMapping("/user.json")
	public SysUser getUser() {
		//throw new PermissionException("permission error");
		//logger.error("UserController.getUser()");
		//logger.info(sysUserMapper.selectByPrimaryKey(1).toString());
		//return sysUserMapper.selectByPrimaryKey(1);
		SysUserMapper userMapper = ApplicationContextHelper.popBean(SysUserMapper.class);
		return userMapper.selectByPrimaryKey(2);
	}
}
