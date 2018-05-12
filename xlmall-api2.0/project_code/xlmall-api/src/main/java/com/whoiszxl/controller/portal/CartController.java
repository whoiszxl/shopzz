package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.service.CartService;
import com.whoiszxl.utils.UserUtil;
import com.whoiszxl.vo.CartVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "前台购物车模块",description="前台购物车模块")
@RestController
@RequestMapping("/cart/")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@ApiOperation(value = "购物车列表")
	@GetMapping("list")
	public ServerResponse<CartVo> list(HttpServletRequest request){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		return cartService.list(user.getId());
	}
	
	@ApiOperation(value = "购物车添加")
	@PostMapping("add")
	public ServerResponse<CartVo> add(HttpServletRequest request, Integer count, Integer productId) {
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		return cartService.add(user.getId(), productId, count);
	}
	
	@ApiOperation(value = "更新购物车商品")
	@PostMapping("update")
	public ServerResponse<CartVo> update(HttpServletRequest request, Integer count, Integer productId) {
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		return cartService.update(user.getId(), productId, count);
	}
	
	@ApiOperation(value = "批量删除购物车商品")
	@PostMapping("delete_product")
	public ServerResponse<CartVo> deleteProduct(HttpServletRequest request,String productIds){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return cartService.deleteProduct(user.getId(),productIds);
    }
	
	@ApiOperation(value = "选中所有购物车商品")
	@PostMapping("select_all")
    public ServerResponse<CartVo> selectAll(HttpServletRequest request){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
    }

	@ApiOperation(value = "不选中购物车所有商品")
    @PostMapping("un_select_all")
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }


	@ApiOperation(value = "购物车里单个选中商品")
    @PostMapping("select")
    public ServerResponse<CartVo> select(HttpServletRequest request,Integer productId){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

	@ApiOperation(value = "购物车里单个不选中商品")
    @PostMapping("un_select")
    public ServerResponse<CartVo> unSelect(HttpServletRequest request,Integer productId){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }


	@ApiOperation(value = "查询当前用户购物车的商品数量")
    @PostMapping("get_cart_product_count")
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request){
		User user = UserUtil.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return cartService.getCartProductCount(user.getId());
    }

}
