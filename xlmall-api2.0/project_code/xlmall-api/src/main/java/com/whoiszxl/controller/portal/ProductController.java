package com.whoiszxl.controller.portal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.service.ProductService;
import com.whoiszxl.vo.ProductDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl 前台商品模块
 */
@Api(value = "前台商品模块", description = "前台商品模块")
@RestController
@RequestMapping("/product/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "前台：通过商品id获取到商品详情", notes = "前台：通过商品id获取到商品详情")
	@GetMapping("detail")
	public ServerResponse<ProductDetailVo> detail(Integer productId) {
		return productService.getProductDetail(productId);
	}

	@ApiOperation(value = "前台：通过商品id获取到商品详情(REST风格)", notes = "前台：通过商品id获取到商品详情(REST风格)")
	@GetMapping("/{productId}")
	public ServerResponse<ProductDetailVo> detailRESTful(@PathVariable Integer productId) {
		return productService.getProductDetail(productId);
	}

	@GetMapping("list")
	@ApiOperation(value = "获取商品列表")
	public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
		return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
	}

	@GetMapping("/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}")
	@ApiOperation(value = "获取商品列表")
	public ServerResponse<PageInfo> listRESTful(
			@PathVariable(value = "keyword") String keyword,
			@PathVariable(value = "categoryId") Integer categoryId, 
			@PathVariable(value = "pageNum") Integer pageNum,
			@PathVariable(value = "pageSize") Integer pageSize, 
			@PathVariable(value = "orderBy") String orderBy) {
		

		pageNum = (pageNum == null) ? 1:pageNum;
		pageSize = (pageNum == null) ? 10:pageSize;
		orderBy = StringUtils.isBlank(orderBy) ? "price_asc":orderBy;
		
		return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
	}
}
