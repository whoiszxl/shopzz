package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JWTUtil;
import com.whoiszxl.jwt.JwtUserService;
import com.whoiszxl.service.SmsService;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;
import com.whoiszxl.vo.UserVo;

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

	@Autowired
	private JwtUserService jwtUserService;

	@Autowired
	private SmsService smsService;

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
			RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),
					Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
		}
		return response;
	}

	/**
	 * jwt用户登录 校验数据库，通过后颁发签名 颁发签名后将签名存入redis做有效性验证 每次校验token的时候先判断redis是否存在token
	 * 登出的时候清除掉token
	 * 
	 * @param username
	 *            用户账号
	 * @param password
	 *            用户密码
	 * @return 用户token
	 */
	@PostMapping("jwt_login")
	@ApiOperation(value = "账号密码登录接口")
	public ServerResponse<String> jwt_login(String username, String password) {
		ServerResponse<String> response = userService.jwt_login(username, password);
		return response;
	}

	@PostMapping("app_login")
	@ApiOperation(value = "APP账号密码登录接口")
	public ServerResponse<UserVo> app_login(String username, String password, String pushId) {
		ServerResponse<UserVo> response = userService.app_login(username, password, pushId);
		return response;
	}

	@PostMapping("app_register")
	@ApiOperation(value = "APP用户手机注册接口")
	public ServerResponse<String> app_register(String phone, String password, String verifyCode) {
		ServerResponse<String> response = userService.app_register(phone, password, verifyCode);
		return response;
	}

	@PostMapping("verifycode")
	@ApiOperation(value = "APP用户手机注册短信验证码接口")
	public ServerResponse<String> verifycode(String phone) {
		ServerResponse<String> response = smsService.sendVerifyCode(phone, Const.SMS.VERIFYCODE_LENGTH);
		return response;
	}

	@PostMapping("forgetpwd_verifycode")
	@ApiOperation(value = "APP忘记密码短信验证码发送接口")
	public ServerResponse<String> forgetpwd_verifycode(String phone) {
		ServerResponse<String> response = smsService.sendForgetPwdVerifyCode(phone, Const.SMS.VERIFYCODE_LENGTH);
		return response;
	}

	@PostMapping("forgetPwd")
	@ApiOperation(value = "APP忘记密码验证接口")
	public ServerResponse<String> forgetPwd(String phone, String verifyCode) {
		ServerResponse<String> response = userService.forgetPwd(phone, verifyCode);
		return response;
	}
	
	@PostMapping("resetPwd")
	@ApiOperation(value = "APP重置密码验证接口")
	public ServerResponse<String> resetPwd(String phone, String password, String verifyCode) {
		ServerResponse<String> response = userService.resetPwd(phone, password, verifyCode);
		return response;
	}

	@PostMapping("logout")
	@ApiOperation(value = "登出接口")
	@RequiresRoles(value = { Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical = Logical.OR)
	public ServerResponse<String> logout(HttpServletResponse response, HttpServletRequest request) {
		return ServerResponse.createBySuccess();
	}

	@PostMapping("register")
	@ApiOperation(value = "用户注册接口")
	public ServerResponse<String> register(User user) {
		ServerResponse<String> response = userService.register(user);
		return response;
	}

	@PostMapping("check_valid")
	public ServerResponse<String> checkValid(String str, String type) {
		return userService.checkVaild(str, type);
	}

	@PostMapping("get_user_info")
	@ApiOperation(value = "获取用户信息的接口")
	@RequiresRoles(value = { Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical = Logical.OR)
	public ServerResponse<User> getUserInfo(HttpServletRequest request) {
		User user = jwtUserService.getCurrentUser(request);
		return ServerResponse.createBySuccess(user);
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
	@RequiresRoles(value = { Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical = Logical.OR)
	public ServerResponse<String> resetPassword(HttpServletRequest request, String passwordOld, String passwordNew) {
		User user = jwtUserService.getCurrentUser(request);
		return userService.resetPassword(passwordOld, passwordNew, user);
	}

	@PostMapping("update_information")
	@ApiOperation(value = "更新用户信息接口")
	@RequiresRoles(value = { Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical = Logical.OR)
	public ServerResponse<User> update_information(HttpServletRequest request, User user) {
		String token = request.getHeader("Authorization");
		String username = JWTUtil.getUsername(token);
		int id = JWTUtil.getUserId(token);
		user.setId(id);
		user.setUsername(username);
		ServerResponse<User> response = userService.updateInformation(user);
		return response;
	}

	@PostMapping("get_information")
	@ApiOperation(value = "获取用户信息的接口")
	@RequiresRoles(value = { Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical = Logical.OR)
	public ServerResponse<User> get_information(HttpServletRequest request) {
		User user = jwtUserService.getCurrentUser(request);
		return ServerResponse.createBySuccess(user);
	}

	@RequestMapping(path = "/401")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ServerResponse<String> unauthorized() {
		return ServerResponse.createByErrorCodeMessage(401, "Unauthorized");
	}
}
