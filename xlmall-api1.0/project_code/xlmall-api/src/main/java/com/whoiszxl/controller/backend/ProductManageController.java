package com.whoiszxl.controller.backend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.whoiszxl.service.FileService;
import com.whoiszxl.service.ProductService;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.PropertiesUtil;

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

	@Autowired
	private ProductService productService;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@PostMapping("save")
	@ApiOperation(value = "后台商品保存")
	public ServerResponse<String> productSave(HttpSession session, Product product) {
		return productService.saveOrUpdateProduct(product);
	}

	@PostMapping("set_sale_status")
	@ApiOperation(value = "后台设置订单状态")
	public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {
		return productService.setSaleStatus(productId, status);
	}

	@GetMapping("detail")
	@ApiOperation(value = "后台获取商品详情")
	public ServerResponse<?> getDetail(HttpSession session, Integer productId) {
		return productService.manageProductDetail(productId);
	}

	@GetMapping("list")
	@ApiOperation(value = "后台获取商品列表")
	public ServerResponse<?> getList(HttpSession session,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return productService.getProductList(pageNum, pageSize);
	}

	@GetMapping("search")
	@ApiOperation(value = "后台搜索商品")
	public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return productService.searchProduct(productName, productId, pageNum, pageSize);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("upload")
	@ApiOperation(value = "后台上传文件")
	public ServerResponse upload(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.upload(file, path);
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

		Map fileMap = Maps.newHashMap();
		fileMap.put("uri", targetFileName);
		fileMap.put("url", url);
		return ServerResponse.createBySuccess(fileMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("richtext_img_upload")
	@ApiOperation(value = "后台富文本图片上传")
	public Map richtextImgUpload(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Map resultMap = Maps.newHashMap();
		// {
		// "success": true/false,
		// "msg": "error message", # optional
		// "file_path": "[real file path]"
		// }
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.upload(file, path);
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

