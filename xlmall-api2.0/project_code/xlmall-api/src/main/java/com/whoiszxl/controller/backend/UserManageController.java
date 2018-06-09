package com.whoiszxl.controller.backend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JwtUserService;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	
	@Autowired
	private JwtUserService jwtUserService;
	
	
	/**
	 * jwt用户登录
	 * 校验数据库，通过后颁发签名
	 * 颁发签名后将签名存入redis做有效性验证
	 * 每次校验token的时候先判断redis是否存在token
	 * 登出的时候清除掉token
	 * @param username 用户账号
	 * @param password 用户密码
	 * @return 用户token
	 */
	@PostMapping("jwt_login")
	@ApiOperation(value = "账号密码登录接口")
	public ServerResponse<String> jwt_login(String username, String password) {
		ServerResponse<String> response = userService.jwt_login(username, password);
		return response;
	}
	
	@PostMapping("userinfo")
	@ApiOperation(value = "获取管理員用户信息的接口")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<User> get_information(HttpServletRequest request) {
		User user = jwtUserService.getCurrentUser(request);
		return ServerResponse.createBySuccess(user);
	}

	@PostMapping("logout")
	@ApiOperation(value = "登出接口")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<String> logout(HttpServletResponse response, HttpServletRequest request) {
		return ServerResponse.createBySuccess();
	}
	
	@PostMapping("list")
	@ApiOperation(value = "获取普通用户列表")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<PageInfo> userList (
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return userService.getUserList(pageNum, pageSize);
	}

}
