package com.whoiszxl.controller.backend;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Product;
import com.whoiszxl.service.ArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 公共文章後台管理模塊
 * @author whoiszxl
 *
 */
@Api(value = "公共文章後台管理模塊", description = "公共文章後台管理模塊")
@RestController
@RequestMapping("/manage/article")
public class ArticleManageController {
	
	private ArticleService articleService;

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

	@GetMapping("detail")
	@ApiOperation(value = "后台获取商品详情")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<?> getDetail(HttpSession session, Integer productId) {
		return productService.manageProductDetail(productId);
	}

	@GetMapping("list")
	@ApiOperation(value = "后台获取商品列表")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse<?> getList(HttpSession session,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return productService.getProductList(pageNum, pageSize);
	}

	@GetMapping("search")
	@ApiOperation(value = "后台搜索商品")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN }, logical=Logical.OR)
	public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return productService.searchProduct(productName, productId, pageNum, pageSize);
	}
}
