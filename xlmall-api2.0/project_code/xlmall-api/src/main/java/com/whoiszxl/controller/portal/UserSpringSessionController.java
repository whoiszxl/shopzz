package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.RedisPool;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;
import com.whoiszxl.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户控制器
 * 
 * @author whoiszxl
 *
 */
@Api(value = "前台用户管理模块(使用了SpringSession)", description = "前台用户管理模块")
@RestController
@RequestMapping("/user/springsession/")
public class UserSpringSessionController {

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("login")
	@ApiOperation(value = "账号密码登录接口")
	public ServerResponse<User> login(String username, String password, HttpSession session,
			HttpServletResponse httpServletResponse) {
		ServerResponse<User> response = userService.login(username, password);
		if (response.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
			// 单点登录:写入token
			// CookieUtil.writeLoginToken(httpServletResponse, session.getId());
			// RedisShardedPoolUtil.setEx(session.getId(),
			// JsonUtil.obj2String(response.getData()),
			// Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
		}
		return response;
	}

	@PostMapping("logout")
	@ApiOperation(value = "登出接口")
	public ServerResponse<String> logout(HttpSession session, HttpServletRequest request) {
		session.removeAttribute(Const.CURRENT_USER);
		// 单点登录：清除cookie和redis
		// String loginToken = CookieUtil.readLoginToken(request);
		// RedisShardedPoolUtil.del(loginToken);
		// CookieUtil.deleteLoginToken(request, response);
		return ServerResponse.createBySuccess();
	}

	@PostMapping("get_user_info")
	@ApiOperation(value = "获取用户信息的接口")
	public ServerResponse<User> getUserInfo(HttpSession session) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		// 单点登录：获取用户信息
		// User user = UserUtil.getCurrentUser(request);
		// if (user == null) {
		// return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
		// }

		if (user != null) {
			return ServerResponse.createBySuccess(user);
		}
		return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
	}

}
