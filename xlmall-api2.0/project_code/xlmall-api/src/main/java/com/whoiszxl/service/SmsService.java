package com.whoiszxl.service;

import com.whoiszxl.common.ServerResponse;

public interface SmsService {
	
	ServerResponse<String> sendVerifyCode(String phoneNumber, int verifyCodeLength);
	
	ServerResponse<String> sendForgetPwdVerifyCode(String phoneNumber, int verifyCodeLength);
}
