package com.whoiszxl.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JWTUtil;
import com.whoiszxl.jwt.JwtUserService;
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
	
	@Autowired
	private JwtUserService jwtUserService;
	
	@ApiOperation(value = "购物车列表")
	@GetMapping("list")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
	public ServerResponse<CartVo> list(HttpServletRequest request){
		User user = jwtUserService.getCurrentUser(request);
		return cartService.list(user.getId());
	}
	
	@ApiOperation(value = "购物车添加")
	@PostMapping("add")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
	public ServerResponse<CartVo> add(HttpServletRequest request, Integer count, Integer productId) {
		User user = jwtUserService.getCurrentUser(request);
		return cartService.add(user.getId(), productId, count);
	}
	
	@ApiOperation(value = "更新购物车商品")
	@PostMapping("update")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
	public ServerResponse<CartVo> update(HttpServletRequest request, Integer count, Integer productId) {
		User user = jwtUserService.getCurrentUser(request);
		return cartService.update(user.getId(), productId, count);
	}
	
	@ApiOperation(value = "批量删除购物车商品")
	@PostMapping("delete_product")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
	public ServerResponse<CartVo> deleteProduct(HttpServletRequest request,String productIds){
		User user = jwtUserService.getCurrentUser(request);
        return cartService.deleteProduct(user.getId(),productIds);
    }
	
	@ApiOperation(value = "选中所有购物车商品")
	@PostMapping("select_all")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<CartVo> selectAll(HttpServletRequest request){
		User user = jwtUserService.getCurrentUser(request);
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
    }

	@ApiOperation(value = "不选中购物车所有商品")
    @PostMapping("un_select_all")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request){
		User user = jwtUserService.getCurrentUser(request);
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }


	@ApiOperation(value = "购物车里单个选中商品")
    @PostMapping("select")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<CartVo> select(HttpServletRequest request,Integer productId){
		User user = jwtUserService.getCurrentUser(request);
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

	@ApiOperation(value = "购物车里单个不选中商品")
    @PostMapping("un_select")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<CartVo> unSelect(HttpServletRequest request,Integer productId){
		User user = jwtUserService.getCurrentUser(request);
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }


	@ApiOperation(value = "查询当前用户购物车的商品数量")
    @PostMapping("get_cart_product_count")
	@RequiresRoles(value={ Const.ShiroRole.ROLE_ADMIN, Const.ShiroRole.ROLE_CUSTOMER }, logical=Logical.OR)
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request){
		User user = jwtUserService.getCurrentUser(request);
        return cartService.getCartProductCount(user.getId());
    }

}
