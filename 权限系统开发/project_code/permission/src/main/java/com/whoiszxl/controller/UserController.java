package com.whoiszxl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.dao.SysUserMapper;
import com.whoiszxl.model.SysUser;

@RestController
public class UserController {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@GetMapping("/user")
	public SysUser getUser() {
		return sysUserMapper.selectByPrimaryKey(1);
	}
}
