package com.whoiszxl.service.impl;


import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.common.TokenCache;
import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JWTUtil;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.MD5Util;
import com.whoiszxl.utils.RedisShardedPoolUtil;

@Service("userService")
public class UserServiveImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public ServerResponse<User> login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		
		//密码md5登录
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		
		User user = userMapper.selectLogin(username, md5Password);
		if(user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		
		user.setPassword(StringUtils.EMPTY);
		
		return ServerResponse.createBySuccess("登录成功",user);
	}
	
	@Override
	public ServerResponse<String> jwt_login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		
		//密码md5登录
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		
		User user = userMapper.selectLogin(username, md5Password);
		if(user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		return ServerResponse.createBySuccess("登录成功",JWTUtil.sign(username, md5Password, user.getId()));
	}
	
	
	public ServerResponse<String> register(User user){
		ServerResponse<String> response = this.checkVaild(user.getUsername(), Const.USERNAME);
		if(!response.isSuccess()) {
			return response;
		}
	
		response = this.checkVaild(user.getEmail(), Const.EMAIL);
		if(!response.isSuccess()) {
			return response;
		}
		
		user.setRole(Const.Role.ROLE_CUSTOMER);
		//MD5加密
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		int resultCount = userMapper.insert(user);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}
	
	
	public ServerResponse<String> checkVaild(String str,String type){
		if(StringUtils.isNoneBlank(type)) {
			if(Const.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUsername(str);
				if(resultCount > 0) {
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			}
			
			if(Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if(resultCount > 0) {
					return ServerResponse.createByErrorMessage("email已存在");
				}
			}
		}else {
			return ServerResponse.createByErrorMessage("参数错误");
		}
		
		return ServerResponse.createBySuccessMessage("验证成功");
	}

	
	public ServerResponse<String> selectQuestion(String username){
		ServerResponse<String> response = this.checkVaild(username, Const.USERNAME);
		if(response.isSuccess()) {
			//用户不存在
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		String question = userMapper.selectQuestionByUsername(username);
		if(StringUtils.isNotBlank(question)) {
			return ServerResponse.createBySuccess(question);
		}
		return ServerResponse.createByErrorMessage("找回密码的问题是空的");
	}

	public ServerResponse<String> checkAnswer(String username,String question,String answer){
		int resultCount = userMapper.checkAnswer(username, question, answer);
		if(resultCount > 0) {
			String forgetToken = UUID.randomUUID().toString();
			//TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
			RedisShardedPoolUtil.setEx(Const.TOKEN_PREFIX + username, forgetToken, 60 * 60 * 12);
			return ServerResponse.createBySuccess(forgetToken);
		}
		return ServerResponse.createByErrorMessage("问题答案错误");
	}
	
	public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
		if(StringUtils.isBlank(forgetToken)) {
			return ServerResponse.createByErrorMessage("参数错误,token不存在");
		}
		ServerResponse<String> response = this.checkVaild(username, Const.USERNAME);
		if(response.isSuccess()) {
			//用户不存在
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		
		//String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
		String token = RedisShardedPoolUtil.get(Const.TOKEN_PREFIX + username);
		if(StringUtils.isBlank(token)) {
			return ServerResponse.createByErrorMessage("token无效或者过期了");
		}
		
		if(StringUtils.equals(forgetToken, token)) {
			String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
			
			if(rowCount > 0) {
				return ServerResponse.createBySuccessMessage("修改密码成功");
			}
		}else {
			return ServerResponse.createByErrorMessage("token错误,请重新重置密码");
		}
		return ServerResponse.createByErrorMessage("修改密码失败");
		
	}
	
	public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
		int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("旧密码错误");
		}
		
		user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
		int updateCount = userMapper.updateByPrimaryKeySelective(user);
		if(updateCount > 0) {
			return ServerResponse.createBySuccessMessage("密码更新成功");
		}
		return ServerResponse.createByErrorMessage("密码更新失败");
	}
	
	public ServerResponse<User> updateInformation(User user) {
		//username不能被更新  修改email需要校验是否重复
		int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
		if(resultCount > 0) {
			return ServerResponse.createByErrorMessage("email已存在,请更换email");
		}
		User updateUser = new User();
		updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
		
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0) {
        	 return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
	}
	
	public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);

    }
	
	//backend

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

}
