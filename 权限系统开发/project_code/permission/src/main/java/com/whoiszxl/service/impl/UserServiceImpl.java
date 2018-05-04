package com.whoiszxl.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.whoiszxl.dao.SysUserMapper;
import com.whoiszxl.exception.ParamException;
import com.whoiszxl.model.SysUser;
import com.whoiszxl.param.UserParam;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.BeanValidator;
import com.whoiszxl.utils.MD5Util;
import com.whoiszxl.utils.PasswordUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public int save(UserParam param) {
		BeanValidator.check(param);
		if(checkTelephoneExist(param.getTelephone(), param.getId())) {
			throw new ParamException("手机号被占用");
		}
		if(checkEmailExist(param.getMail(), param.getId())) {
			throw new ParamException("邮箱被占用");
		}
		
		//String password = PasswordUtil.randomPassword();
		String password = "123456";
		String encryptedPassword = MD5Util.encrypt(password);
		SysUser user = new SysUser();
		BeanUtils.copyProperties(param, user);
		user.setPassword(encryptedPassword);
		user.setOperator("system");//TODO：当前操作的用户
		user.setOperateIp("127.0.0.1");//TODO：当前操作的IP
		user.setOperateTime(new Date());
		
		//TODO: 发送邮件
		
		return sysUserMapper.insertSelective(user);
	}
	
	@Override
	public int update(UserParam param) {
		BeanValidator.check(param);
		if(checkTelephoneExist(param.getTelephone(), param.getId())) {
			throw new ParamException("手机号被占用");
		}
		if(checkEmailExist(param.getMail(), param.getId())) {
			throw new ParamException("邮箱被占用");
		}
		
		SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的用户并不存在");
		SysUser after = new SysUser();
		BeanUtils.copyProperties(param, after);
		after.setId(before.getId());
		return sysUserMapper.updateByPrimaryKeySelective(after);
	}

	@Override
	public boolean checkEmailExist(String mail, Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkTelephoneExist(String phone, Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
