package com.whoiszxl.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "后台用户管理模块", description = "后台用户管理模块")
@RestController
@RequestMapping("/manage/user")
public class UserManageController {

	@Autowired
	private UserService userService;

	@PostMapping("login")
	@ApiOperation(value = "后台管理员登录")
	public ServerResponse<User> login(String username, String password, HttpSession session) {
		ServerResponse<User> response = userService.login(username, password);
		if (response.isSuccess()) {
			User user = response.getData();
			if (user.getRole() == Const.Role.ROLE_ADMIN) {
				// 登录的是管理员
				session.setAttribute(Const.CURRENT_USER, user);
				return response;
			} else {
				return ServerResponse.createByErrorMessage("不是管理员无法登录");
			}
		}
		return response;
	}

}
