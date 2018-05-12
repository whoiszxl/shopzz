package com.whoiszxl.controller.backend;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisPoolUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

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
	public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
		ServerResponse<User> response = userService.login(username, password);
		if (response.isSuccess()) {
			User user = response.getData();
			if (user.getRole() == Const.Role.ROLE_ADMIN) {
				// 登录的是管理员
				//session.setAttribute(Const.CURRENT_USER, user);
				// 单点登录:写入token
				CookieUtil.writeLoginToken(httpServletResponse, session.getId());
				RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),
						Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
				return response;
			} else {
				return ServerResponse.createByErrorMessage("不是管理员无法登录");
			}
		}
		return response;
	}

}
