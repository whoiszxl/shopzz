package com.whoiszxl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.ApplicationContextHelper;
import com.whoiszxl.common.JsonData;
import com.whoiszxl.dao.SysUserMapper;
import com.whoiszxl.exception.PermissionException;
import com.whoiszxl.model.SysUser;
import com.whoiszxl.param.UserParam;
import com.whoiszxl.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sys/user")
@Api(value="系统用户模块", description="系统用户模块")
public class SysUserController {
	
	private Logger logger = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save.json")
	@ApiOperation(value = "保存系统用户接口")
	public JsonData saveUser(UserParam param) {
		int result = userService.save(param);
		if(result == 1) {
			return JsonData.success("保存用户成功");
		}else {
			return JsonData.fail("保存用户失败");
		}
	}
	
	@PostMapping("/update.json")
	@ApiOperation(value = "更新系统用户接口")
	public JsonData updateUser(UserParam param) {
		int result = userService.update(param);
		if(result == 1) {
			return JsonData.success("更新用户成功");
		}else {
			return JsonData.fail("更新用户失败");
		}
	}
}
