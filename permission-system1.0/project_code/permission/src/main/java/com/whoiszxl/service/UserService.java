package com.whoiszxl.service;

import com.whoiszxl.model.SysUser;
import com.whoiszxl.param.UserParam;

/**
 * 用户服务
 * @author whoiszxl
 *
 */
public interface UserService {
	
	/**
	 * 保存一个用户
	 * @param param 前台接收到的用户参数
	 */
	int save(UserParam param);
	
	
	/**
	 * 更新一个用户
	 * @param param 前台接收到的用户参数
	 */
	int update(UserParam param);
	
	/**
	 * 检验邮箱是否有效
	 * @param mail 邮箱
	 * @param userId 用户id
	 * @return 是否有效
	 */
	boolean checkEmailExist(String mail, Integer userId);
	
	/**
	 * 检验手机号是否有效
	 * @param phone 手机号
	 * @param userId 用户id
	 * @return 是否有效
	 */
	boolean checkTelephoneExist(String phone, Integer userId);

	/**
	 * 通过用户名或者邮箱查询到这个用户
	 * @param keyword
	 * @return 查询到的用户实体
	 */
	SysUser findByKeyword(String keyword);

	


	
}
