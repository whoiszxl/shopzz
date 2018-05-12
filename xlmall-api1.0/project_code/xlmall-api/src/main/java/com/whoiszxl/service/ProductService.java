package com.whoiszxl.service;

import com.github.pagehelper.PageInfo;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.Product;
import com.whoiszxl.vo.ProductDetailVo;
/**
 * 
 * @author whoiszxl
 *
 */
public interface ProductService {
	ServerResponse<String> saveOrUpdateProduct(Product product);

	ServerResponse<String> setSaleStatus(Integer productId, Integer status);

	ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

	ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

	ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

	ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

	ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize,
			String orderBy);
}

