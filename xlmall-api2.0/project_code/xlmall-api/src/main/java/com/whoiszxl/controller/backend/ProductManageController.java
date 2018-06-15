package com.whoiszxl.controller.backend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Product;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JWTToken;
import com.whoiszxl.jwt.JWTUtil;
import com.whoiszxl.service.FileService;
import com.whoiszxl.service.ProductService;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value="后台商品管理模块",description="后台商品管理模块")
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductManageController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@PostMapping("save")
	@ApiOperation(value = "后台商品保存")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN } )
	public ServerResponse<String> productSave(HttpSession session, Product product) {
		return productService.saveOrUpdateProduct(product);
	}

	@PostMapping("set_sale_status")
	@ApiOperation(value = "后台设置订单状态")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {
		return productService.setSaleStatus(productId, status);
	}

	@PostMapping("detail")
	@ApiOperation(value = "后台获取商品详情")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<?> getDetail(HttpSession session, Integer productId) {
		return productService.manageProductDetail(productId);
	}

	@PostMapping("list")
	@ApiOperation(value = "后台获取商品列表")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<?> getList(HttpSession session,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return productService.getManageProductList(pageNum, pageSize);
	}

	@PostMapping("search")
	@ApiOperation(value = "后台搜索商品")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return productService.searchProduct(productName, productId, pageNum, pageSize);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("upload")
	@ApiOperation(value = "后台上传文件")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse upload(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.uploadToQiniu(file, path);
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

		Map fileMap = Maps.newHashMap();
		fileMap.put("uri", targetFileName);
		fileMap.put("url", url);
		return ServerResponse.createBySuccess(fileMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("richtext_img_upload_bak")
	@ApiOperation(value = "后台富文本图片上传")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public Map richtextImgUpload_bak(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Map resultMap = Maps.newHashMap();
		// {
		// "success": true/false,
		// "msg": "error message", # optional
		// "file_path": "[real file path]"
		// }
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.uploadToQiniu(file, path);
		if (StringUtils.isBlank(targetFileName)) {
			resultMap.put("success", false);
			resultMap.put("msg", "上传失败");
			return resultMap;
		}
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
		resultMap.put("success", true);
		resultMap.put("msg", "上传成功");
		resultMap.put("file_path", url);
		response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
		return resultMap;

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("richtext_img_upload")
	@ApiOperation(value = "后台富文本图片上传")
	public Map richtextImgUpload(HttpSession session,
			@RequestParam(value = "auth_token",required = true) String auth_token,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		
		//用的simditor貌似不支持header頭，干！又要重寫一個post參數鑒權的接口
		
		//先判斷redis是否存在
        String have = RedisShardedPoolUtil.get(auth_token);
        if(!StringUtils.equals(have, "1")) {
        	logger.error("token已经在redis失效了");
        	throw new RuntimeException("token已经在redis失效了");
        }
        
        //再校驗一下token的用戶名是否在數據庫存在吧，沒有傳密碼過來無法對token進行校驗
        Integer userId = JWTUtil.getUserId(auth_token);
        ServerResponse<User> information = userService.getInformation(userId);
        if(!information.isSuccess()) {
        	logger.error("token里根本沒有這個用戶，無效");
        	throw new RuntimeException("token不存在當前用戶，無效");
        }
		
		Map resultMap = Maps.newHashMap();
		// {
		// "success": true/false,
		// "msg": "error message", # optional
		// "file_path": "[real file path]"
		// }
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.uploadToQiniu(file, path);
		if (StringUtils.isBlank(targetFileName)) {
			resultMap.put("success", false);
			resultMap.put("msg", "上传失败");
			return resultMap;
		}
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
		resultMap.put("success", true);
		resultMap.put("msg", "上传成功");
		resultMap.put("file_path", url);
		response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
		return resultMap;

	}
}

