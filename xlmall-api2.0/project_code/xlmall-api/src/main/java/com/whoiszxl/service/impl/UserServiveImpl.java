package com.whoiszxl.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.UserMapper;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JWTUtil;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.MD5Util;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;
import com.whoiszxl.vo.UserVo;

@Service("userService")
public class UserServiveImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	private static final long EXPIRE_TIME = Integer
			.parseInt(PropertiesUtil.getProperty("jwt.token.expire.time", "60000"));

	@Override
	public ServerResponse<User> login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}

		// 密码md5登录
		User user = userMapper.selectLogin(username, MD5Util.MD5EncodeUtf8(password));
		if (user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}

		user.setPassword(StringUtils.EMPTY);

		return ServerResponse.createBySuccess("登录成功", user);
	}

	@Override
	public ServerResponse<String> jwt_login(String username, String password) {
		int resultCount = userMapper.checkUsername(username);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}

		// 密码md5登录
		User user = userMapper.selectLogin(username, MD5Util.MD5EncodeUtf8(password));
		if (user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		String token = JWTUtil.sign(username, password, user.getId());
		RedisShardedPoolUtil.setEx(token, "1", (int) (Const.JWTTokenCache.JWT_TOKEN_EXTIME / 1000));
		return ServerResponse.createBySuccess("登录成功", token);
	}

	public ServerResponse<String> register(User user) {
		ServerResponse<String> response = this.checkVaild(user.getUsername(), Const.USERNAME);
		if (!response.isSuccess()) {
			return response;
		}

		response = this.checkVaild(user.getEmail(), Const.EMAIL);
		if (!response.isSuccess()) {
			return response;
		}

		user.setRole(Const.Role.ROLE_CUSTOMER);
		// MD5加密
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		int resultCount = userMapper.insert(user);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	public ServerResponse<String> checkVaild(String str, String type) {
		if (StringUtils.isNoneBlank(type)) {
			if (Const.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUsername(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			}

			if (Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMessage("email已存在");
				}
			}

			if (Const.PHONE.equals(type)) {
				int resultCount = userMapper.checkPhone(str);
				if (resultCount > 0) {
					return ServerResponse.createByErrorMessage("手机号已存在");
				}
			}
		} else {
			return ServerResponse.createByErrorMessage("参数错误");
		}

		return ServerResponse.createBySuccessMessage("验证成功");
	}

	public ServerResponse<String> selectQuestion(String username) {
		ServerResponse<String> response = this.checkVaild(username, Const.USERNAME);
		if (response.isSuccess()) {
			// 用户不存在
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		String question = userMapper.selectQuestionByUsername(username);
		if (StringUtils.isNotBlank(question)) {
			return ServerResponse.createBySuccess(question);
		}
		return ServerResponse.createByErrorMessage("找回密码的问题是空的");
	}

	public ServerResponse<String> checkAnswer(String username, String question, String answer) {
		int resultCount = userMapper.checkAnswer(username, question, answer);
		if (resultCount > 0) {
			String forgetToken = UUID.randomUUID().toString();
			// TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
			RedisShardedPoolUtil.setEx(Const.TOKEN_PREFIX + username, forgetToken, 60 * 60 * 12);
			return ServerResponse.createBySuccess(forgetToken);
		}
		return ServerResponse.createByErrorMessage("问题答案错误");
	}

	public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
		if (StringUtils.isBlank(forgetToken)) {
			return ServerResponse.createByErrorMessage("参数错误,token不存在");
		}
		ServerResponse<String> response = this.checkVaild(username, Const.USERNAME);
		if (response.isSuccess()) {
			// 用户不存在
			return ServerResponse.createByErrorMessage("用户不存在");
		}

		// String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
		String token = RedisShardedPoolUtil.get(Const.TOKEN_PREFIX + username);
		if (StringUtils.isBlank(token)) {
			return ServerResponse.createByErrorMessage("token无效或者过期了");
		}

		if (StringUtils.equals(forgetToken, token)) {
			String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userMapper.updatePasswordByUsername(username, md5Password);

			if (rowCount > 0) {
				return ServerResponse.createBySuccessMessage("修改密码成功");
			}
		} else {
			return ServerResponse.createByErrorMessage("token错误,请重新重置密码");
		}
		return ServerResponse.createByErrorMessage("修改密码失败");

	}

	public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
		int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("旧密码错误");
		}

		user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
		int updateCount = userMapper.updateByPrimaryKeySelective(user);
		if (updateCount > 0) {
			return ServerResponse.createBySuccessMessage("密码更新成功");
		}
		return ServerResponse.createByErrorMessage("密码更新失败");
	}

	public ServerResponse<User> updateInformation(User user) {
		// username不能被更新 修改email需要校验是否重复
		int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
		if (resultCount > 0) {
			return ServerResponse.createByErrorMessage("email已存在,请更换email");
		}
		User updateUser = new User();
		updateUser.setId(user.getId());
		updateUser.setEmail(user.getEmail());
		updateUser.setPhone(user.getPhone());
		updateUser.setQuestion(user.getQuestion());
		updateUser.setAnswer(user.getAnswer());

		int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
		if (updateCount > 0) {
			return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
		}
		return ServerResponse.createByErrorMessage("更新个人信息失败");
	}

	public ServerResponse<User> getInformation(Integer userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return ServerResponse.createByErrorMessage("找不到当前用户");
		}
		user.setPassword(StringUtils.EMPTY);
		return ServerResponse.createBySuccess(user);

	}

	// backend

	/**
	 * 校验是否是管理员
	 * 
	 * @param user
	 * @return
	 */
	public ServerResponse checkAdminRole(User user) {
		if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
			return ServerResponse.createBySuccess();
		}
		return ServerResponse.createByError();
	}

	@Override
	public ServerResponse<PageInfo> getUserList(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> userList = userMapper.selectAllUser();
		PageInfo pageResult = new PageInfo<>(userList);
		return ServerResponse.createBySuccess(pageResult);
	}

	@Override
	public int selectUserCount() {
		return userMapper.selectUserCount();
	}

	@Override
	public ServerResponse<UserVo> app_login(String phone, String password, String pushId) {
		int resultCount = userMapper.checkPhone(phone);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("手机号不存在");
		}

		// 密码md5登录
		User user = userMapper.selectLoginByPhone(phone, MD5Util.MD5EncodeUtf8(password));
		if (user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}

		// 签名token并存入redis
		String token = JWTUtil.sign(user.getUsername(), password, user.getId());
		RedisShardedPoolUtil.setEx(token, "1", (int) (Const.JWTTokenCache.JWT_TOKEN_EXTIME / 1000));

		// app登录后还需要更新push_id推送消息id
		User pushUser = new User();
		pushUser.setId(user.getId());
		pushUser.setPushId(pushId);
		pushUser.setLastLoginTime(new Date());
		userMapper.updateByPrimaryKeySelective(pushUser);

		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(user, userVo);
		userVo.setToken(token);
		return ServerResponse.createBySuccess("登录成功", userVo);
	}

	@Override
	public ServerResponse<String> app_register(String phone, String password, String verifyCode) {
		// 校验用户是否存在
		ServerResponse<String> response = this.checkVaild(phone, Const.PHONE);
		if (!response.isSuccess()) {
			return response;
		}

		// 校验传递过来的验证码是否有效
		if (!RedisShardedPoolUtil.get(Const.SMS.SMS_ALI_VERIFY_CODE_PREFIX + phone).equals(verifyCode)) {
			return ServerResponse.createByErrorMessage("验证码已经失效");
		}

		// 全部校验通过了之后呢构建一个用户对象保存到数据库中
		User user = new User();
		user.setUsername(phone);
		// MD5加密
		user.setPassword(MD5Util.MD5EncodeUtf8(password));
		user.setRole(Const.Role.ROLE_CUSTOMER);
		Date currentTime = new Date();
		user.setCreateTime(currentTime);
		user.setUpdateTime(currentTime);

		int resultCount = userMapper.insertSelective(user);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("注册失败");
		}
		// 注册成功后删除redis中的验证码
		RedisShardedPoolUtil.del(Const.SMS.SMS_ALI_VERIFY_CODE_PREFIX + phone);
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	@Override
	public ServerResponse<String> forgetPwd(String phone, String verifyCode) {
		// 校验用户是否存在
		ServerResponse<String> response = this.checkVaild(phone, Const.PHONE);
		if (response.isSuccess()) {
			return ServerResponse.createByErrorMessage("该手机号用户不存在");
		}

		// 校验传递过来的验证码是否有效
		if (!RedisShardedPoolUtil.get(Const.SMS.SMS_ALI_FORGET_PWD_PREFIX + phone).equals(verifyCode)) {
			return ServerResponse.createByErrorMessage("验证码已经失效");
		}

		return ServerResponse.createBySuccessMessage("校验成功");
	}

	@Override
	public ServerResponse<String> resetPwd(String phone, String password, String verifyCode) {
		// 校验用户是否存在
		ServerResponse<String> response = this.checkVaild(phone, Const.PHONE);
		if (response.isSuccess()) {
			return ServerResponse.createByErrorMessage("该手机号用户不存在");
		}

		// 校验传递过来的验证码是否有效
		if (!RedisShardedPoolUtil.get(Const.SMS.SMS_ALI_FORGET_PWD_PREFIX + phone).equals(verifyCode)) {
			return ServerResponse.createByErrorMessage("验证码已经失效");
		}

		// 全部校验通过了之后呢需要重置一下当前这个用户的密码
		
		User currentUser = userMapper.selectUserByPhone(phone);
		// MD5加密
		currentUser.setPassword(MD5Util.MD5EncodeUtf8(password));
		currentUser.setUpdateTime(new Date());

		int resultCount = userMapper.updateByPrimaryKeySelective(currentUser);
		if (resultCount == 0) {
			return ServerResponse.createByErrorMessage("密码修改失败");
		}
		// 修改成功后删除redis中的验证码
		RedisShardedPoolUtil.del(Const.SMS.SMS_ALI_FORGET_PWD_PREFIX + phone);
		return ServerResponse.createBySuccessMessage("密码修改成功");
	}

}
