package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qiniu.util.Auth;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.utils.PropertiesUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "公共调用模块", description = "公共调用模块")
@RestController
@RequestMapping("/common/")
public class CommonController {
	
	@PostMapping("getUploadToken")
	@ApiOperation(value = "获取七牛云上传凭证")
	//@RequiresRoles(value = { Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical = Logical.OR)
	public ServerResponse<String> getUploadToken(HttpServletRequest request) {
		String accessKey = PropertiesUtil.getProperty("qiniu.accessKey");
		String secretKey = PropertiesUtil.getProperty("qiniu.secretKey");
		String bucket = PropertiesUtil.getProperty("qiniu.bucket");
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        
        return ServerResponse.createBySuccess(upToken);
	}
}
