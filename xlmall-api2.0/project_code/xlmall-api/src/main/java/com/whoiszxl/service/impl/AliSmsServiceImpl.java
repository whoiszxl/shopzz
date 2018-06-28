package com.whoiszxl.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.service.SmsService;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.AliSmsSender;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

@Service
public class AliSmsServiceImpl implements SmsService{

	@Autowired
	private UserService userService;
	
	@Override
	public ServerResponse<String> sendVerifyCode(String phoneNumber, int verifyCodeLength) {
		
		//發送驗證碼之前先校验手机号是否存在
		ServerResponse<String> response = userService.checkVaild(phoneNumber, Const.PHONE);
		if(!response.isSuccess()) {
			return response;
		}
		
		try {
			String verifyCode = AliSmsSender.getRandomNumCode(verifyCodeLength);
			SendSmsResponse result = AliSmsSender.sendVerifyCode(phoneNumber, verifyCode);
			String message = result.getMessage();
			if(!StringUtils.equals("OK", message)) {
				return ServerResponse.createByErrorMessage("发送验证码失败");
			}
			
			//发送成功之后需要存储验证码到redis中
			RedisShardedPoolUtil.setEx(
					Const.SMS.SMS_ALI_VERIFY_CODE_PREFIX+phoneNumber, 
					verifyCode, 
					Integer.parseInt(PropertiesUtil.getProperty("alisms.verifyCode.expire.time")));
			
			return ServerResponse.createBySuccessMessage("发送验证码成功");
		} catch (ClientException e) {
			return ServerResponse.createByErrorMessage("发送验证码失败");
		}
	}

	
}
