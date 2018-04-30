package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.CategoryMapper;
import com.whoiszxl.dao.ProductMapper;
import com.whoiszxl.entity.Category;
import com.whoiszxl.entity.Product;
import com.whoiszxl.service.CategoryService;
import com.whoiszxl.service.ProductService;
import com.whoiszxl.utils.DateTimeUtil;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.vo.ProductDetailVo;
import com.whoiszxl.vo.ProductListVo;

/**
 * 
 * @author whoiszxl
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private CategoryService categoryService;

	public ServerResponse<String> saveOrUpdateProduct(Product product) {
		if (product != null) {
			if (StringUtils.isNotBlank(product.getSubImages())) {
				String[] subImageArray = product.getSubImages().split(",");
				if (subImageArray.length > 0) {
					product.setMainImage(subImageArray[0]);
				}
			}

			if (product.getId() != null) {
				int rowCount = productMapper.updateByPrimaryKey(product);
				if (rowCount > 0) {
					return ServerResponse.createBySuccessMessage("更新产品成功");
				} else {
					return ServerResponse.createByErrorMessage("更新产品失败");
				}

			} else {
				int rowCount = productMapper.insert(product);
				if (rowCount > 0) {
					return ServerResponse.createBySuccessMessage("新增产品成功");
				} else {
					return ServerResponse.createByErrorMessage("新增产品失败");
				}
			}
		}
		return ServerResponse.createByErrorMessage("新增或更新产品参数不正确了");
	}

	public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
		if (productId == null || status == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
					ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		Product product = new Product();
		product.setId(productId);
		product.setStatus(status);
		int rowCount = productMapper.updateByPrimaryKeySelective(product);
		if (rowCount > 0) {
			return ServerResponse.createBySuccess("修改产品销售状态成功");
		}
		return ServerResponse.createByErrorMessage("修改产品销售状态失败");
	}

	public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
		if (productId == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
					ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		// 通过id查询到商品
		Product product = productMapper.selectByPrimaryKey(productId);
		// 判断是否存在
		if (product == null) {
			return ServerResponse.createByErrorMessage("产品已下架或者删除");
		}
		// 将商品对象转换成vo对象
		ProductDetailVo productDetailVo = assembleProductDetailVo(product);
		return ServerResponse.createBySuccess(productDetailVo);
	}

	private ProductDetailVo assembleProductDetailVo(Product product) {
		ProductDetailVo productDetailVo = new ProductDetailVo();
		productDetailVo.setId(product.getId());
		productDetailVo.setSubtitle(product.getSubtitle());
		productDetailVo.setPrice(product.getPrice());
		productDetailVo.setMainImage(product.getMainImage());
		productDetailVo.setSubImages(product.getSubImages());
		productDetailVo.setCategoryId(product.getCategoryId());
		productDetailVo.setDetail(product.getDetail());
		productDetailVo.setName(product.getName());
		productDetailVo.setStatus(product.getStatus());
		productDetailVo.setStock(product.getStock());

		//设置图片路径
		productDetailVo
				.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://image.chenyuspace.com/"));
		
		//通过商品的分类id获取到分类信息
		Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
		if (category == null) {
			//设置vo对象的父级id为根节点
			productDetailVo.setParentCategoryId(0);// 默认根节点
		} else {
			//设置为分类的父级id
			productDetailVo.setParentCategoryId(category.getParentId());
		}
		//转换时间格式
		productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
		productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
		return productDetailVo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        //查询到商品的所有数据
        List<Product> productList = productMapper.selectList();
        //新建volist通过遍历转换
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        //将对象加到分页对象
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }
	
	private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.chenyuspace.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

	@Override
	public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
		//分页开启
		PageHelper.startPage(pageNum,pageSize);
		//拼接 % %
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        //通过name和id批量查询商品
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        //转换成vo对象
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        //分页内容换成vo的
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
	}

	@Override
	public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
		if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
		//通过id查询到商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        //转换成vo对象返回给前端
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
	}

	@Override
	public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum,
			int pageSize, String orderBy) {
		//关键字和分类id都不存在，参数错误
		if(StringUtils.isBlank(keyword) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();

        if(categoryId != null){
        	//通过分类id查询到分类
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){
                //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            //分类子节点集合
            categoryIdList = categoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        //如果关键字不为空 给关键字拼接 % %
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
        	//如果排序的方式 在  asc 和 desc中
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            	//切割字段和排序方式
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        //通过关键字和分类的ids获取商品列表
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);

        //转换vo
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }

        //包装返回
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
	}
	
}

