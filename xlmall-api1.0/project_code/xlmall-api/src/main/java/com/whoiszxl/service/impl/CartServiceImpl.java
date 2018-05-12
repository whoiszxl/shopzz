package com.whoiszxl.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.dao.CartMapper;
import com.whoiszxl.dao.ProductMapper;
import com.whoiszxl.entity.Cart;
import com.whoiszxl.entity.Product;
import com.whoiszxl.service.CartService;
import com.whoiszxl.utils.BigDecimalUtil;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.vo.CartProductVo;
import com.whoiszxl.vo.CartVo;

/**
 * 购物车服务实现接口
 * @author whoiszxl
 *
 */
@Service("iCartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private ProductMapper productMapper;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	
	
	public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
		
		if(productId == null || count == null){
			logger.error("参数错误");
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
		
		//验证产品是否存在
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null) {
			 return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"添加购物车的产品不存在");
		}
		
		Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
		if (cart == null) {
			logger.info("进行购物车添加商品了");
			// 这个产品不在当前用户的购物车内
			Cart cartItem = new Cart();
			cartItem.setQuantity(count);
			cartItem.setChecked(Const.Cart.CHECKED);
			cartItem.setProductId(productId);
			cartItem.setUserId(userId);

			int rowCount = cartMapper.insert(cartItem);
			if(rowCount > 0) {
				logger.info("购物车item添加成功");
			}
		} else {
			// 产品已经在购物车里
			cart.setQuantity(cart.getQuantity() + count);
			cartMapper.updateByPrimaryKeySelective(cart);
			logger.info("产品已经在购物车里直接加上数据");
		}
		
		return this.list(userId);
	}

	
	public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
		if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
		
		Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
		if(cart != null) {
			cart.setQuantity(count);
		}
		cartMapper.updateByPrimaryKeySelective(cart);
		return this.list(userId);
	}
	
	public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds){
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productList);
        return this.list(userId);
    }
	
	
	public ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }
	
	
	
	public ServerResponse<CartVo> list (Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }
	
	private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(cartList)){
            for(Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVo;
    }
	
	private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;

    }
}
