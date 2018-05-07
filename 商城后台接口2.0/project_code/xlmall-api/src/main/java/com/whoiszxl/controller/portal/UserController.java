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
import com.whoiszxl.utils.RedisPoolUtil;
import com.whoiszxl.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户控制器
 * 
 * @author whoiszxl
 *
 */
@Api(value = "前台用户管理模块", description = "前台用户管理模块")
@RestController
@RequestMapping("/user/")
public class UserController {

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
			// session.setAttribute(Const.CURRENT_USER, response.getData());

			// 单点登录:写入token
			CookieUtil.writeLoginToken(httpServletResponse, session.getId());
			RedisPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),
					Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
		}
		return response;
	}

	@PostMapping("logout")
	@ApiOperation(value = "登出接口")
	public ServerResponse<String> logout(HttpServletResponse response, HttpServletRequest request) {
		//session.removeAttribute(Const.CURRENT_USER);
		//单点登录：清除cookie和redis
		String loginToken = CookieUtil.readLoginToken(request);
		RedisPoolUtil.del(loginToken);
		CookieUtil.deleteLoginToken(request, response);
		return ServerResponse.createBySuccess();
	}

	@PostMapping("register")
	@ApiOperation(value = "用户注册接口")
	public ServerResponse<String> register(User user) {
		ServerResponse<String> response = userService.register(user);
		return response;
	}

	@PostMapping("check_valid")
	@ApiOperation(value = "校验用户名邮箱参数登录")
	public ServerResponse<String> checkValid(String str, String type) {
		return userService.checkVaild(str, type);
	}

	@PostMapping("get_user_info")
	@ApiOperation(value = "获取用户信息的接口")
	public ServerResponse<User> getUserInfo(HttpServletRequest request) {
		//User user = (User) session.getAttribute(Const.CURRENT_USER);
		//单点登录：获取用户信息
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
        }
		
		if (user != null) {
			return ServerResponse.createBySuccess(user);
		}
		return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
	}

	@PostMapping("forget_get_question")
	@ApiOperation(value = "获取密保问题接口")
	public ServerResponse<String> forgetGetQuestion(String username) {
		return userService.selectQuestion(username);
	}

	@PostMapping("forget_check_answer")
	@ApiOperation(value = "校验密保问题答案的接口")
	public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
		return userService.checkAnswer(username, question, answer);
	}

	@PostMapping("forget_reset_password")
	@ApiOperation(value = "重置用户密码接口")
	public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
		return userService.forgetResetPassword(username, passwordNew, forgetToken);
	}

	@PostMapping("reset_password")
	@ApiOperation(value = "通过旧密码重置密码的接口")
	public ServerResponse<String> resetPassword(HttpServletRequest request, String passwordOld, String passwordNew) {
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
        }
		return userService.resetPassword(passwordOld, passwordNew, user);
	}

	@PostMapping("update_information")
	@ApiOperation(value = "更新用户信息接口")
	public ServerResponse<User> update_information(HttpServletRequest request, User user) {
		User currentUser = UserUtil.getCurrentUser(request);
        if(currentUser == null) {
        	return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
        }
		user.setId(currentUser.getId());
		user.setUsername(currentUser.getUsername());
		ServerResponse<User> response = userService.updateInformation(user);
		if (response.isSuccess()) {
			response.getData().setUsername(currentUser.getUsername());
			String loginToken = CookieUtil.readLoginToken(request);
			RedisPoolUtil.setEx(loginToken, JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
		}

		return response;
	}

	@PostMapping("get_information")
	@ApiOperation(value = "获取用户信息的接口")
	public ServerResponse<User> get_information(HttpServletRequest request) {
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		return userService.getInformation(user.getId());
	}
}
