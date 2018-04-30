package com.whoiszxl.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.service.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * 用户控制器
 * @author whoiszxl
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("login")
	@ApiOperation(value = "账号密码登录接口")
	public ServerResponse<User> login(String username,String password,HttpSession session) {
		ServerResponse<User> response = userService.login(username, password);
		if(response.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
	}
	
	@PostMapping("logout")
	@ApiOperation(value = "登出接口")
	public ServerResponse<String> logout(HttpSession session){
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess();
	}
	
	@PostMapping("register")
	@ApiOperation(value = "用户注册接口")
	public ServerResponse<String> register(User user){
		ServerResponse<String> response = userService.register(user);
		return response;
	}
	
	@PostMapping("check_valid")
	@ApiOperation(value = "校验用户名邮箱参数登录")
	public ServerResponse<String> checkValid(String str,String type){
		return userService.checkVaild(str, type);
	}
	
	@PostMapping("get_user_info")
	@ApiOperation(value = "获取用户信息的接口")
	public ServerResponse<User> getUserInfo(HttpSession session){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user != null) {
			return ServerResponse.createBySuccess(user);
		}
		return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
	}
	
	@PostMapping("forget_get_question")
	@ApiOperation(value = "获取密保问题接口")
	public ServerResponse<String> forgetGetQuestion(String username){
		 return userService.selectQuestion(username);
	}
	
	@PostMapping("forget_check_answer")
	@ApiOperation(value = "校验密保问题答案的接口")
	public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
		return userService.checkAnswer(username, question, answer);
	}
	
	@PostMapping("forget_reset_password")
	@ApiOperation(value = "重置用户密码接口")
	public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
		return userService.forgetResetPassword(username, passwordNew, forgetToken);
	}
	
	@PostMapping("reset_password")
	@ApiOperation(value = "通过旧密码重置密码的接口")
	public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user == null) {
			return ServerResponse.createByErrorMessage("用户未登录");
		}
		return userService.resetPassword(passwordOld, passwordNew, user);
	}
	
	@PostMapping("update_information")
	@ApiOperation(value = "更新用户信息接口")
	public ServerResponse<User> update_information(HttpSession session,User user){
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if(currentUser == null) {
			return ServerResponse.createByErrorMessage("用户未登录");
		} 
		user.setId(currentUser.getId());
		user.setUsername(currentUser.getUsername());
		ServerResponse<User> response = userService.updateInformation(user);
		if(response.isSuccess()) {
			response.getData().setUsername(currentUser.getUsername());
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		
		return response;
	}
	
	
	@PostMapping("get_information")
	@ApiOperation(value = "获取用户信息的接口")
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return userService.getInformation(currentUser.getId());
    }
}
