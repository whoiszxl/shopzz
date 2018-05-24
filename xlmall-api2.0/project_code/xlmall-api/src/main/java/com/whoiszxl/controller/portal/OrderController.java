package com.whoiszxl.controller.portal;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ResponseCode;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.jwt.JwtUserService;
import com.whoiszxl.service.OrderService;
import com.whoiszxl.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "前台订单模块",description = "前台订单模块")
@RestController
@RequestMapping("/order/")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JwtUserService jwtUserService;
	
	
	@PostMapping("create")
	@ApiOperation(value = "创建订单接口")
	@RequiresRoles(value={"0","1"}, logical=Logical.OR)
	public ServerResponse create(HttpServletRequest request,Integer shippingId) {
		User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		return orderService.createOrder(user.getId(),shippingId);
	}
	
	
	@PostMapping("cancel")
	@ApiOperation(value = "取消订单接口")
	@RequiresRoles(value={"0","1"}, logical=Logical.OR)
	public ServerResponse cancel(HttpServletRequest request,Long orderNo) {
		User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		return orderService.cancel(user.getId(), orderNo);
	}
	
	@PostMapping("get_order_cart_product")
	@ApiOperation(value = "获取订单的商品集合,缩略图和总价接口")
	@RequiresRoles(value={"0","1"}, logical=Logical.OR)
    public ServerResponse getOrderCartProduct(HttpServletRequest request){
		User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return orderService.getOrderCartProduct(user.getId());
    }



    @GetMapping("detail")
    @ApiOperation(value = "获取订单详情接口")
    @RequiresRoles(value={"0","1"}, logical=Logical.OR)
    public ServerResponse detail(HttpServletRequest request,Long orderNo){
    	User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return orderService.getOrderDetail(user.getId(),orderNo);
    }

    @GetMapping("list")
    @ApiOperation(value = "获取订单列表接口")
    @RequiresRoles(value={"0","1"}, logical=Logical.OR)
    public ServerResponse list(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
    	User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return orderService.getOrderList(user.getId(),pageNum,pageSize);
    }

	
	
	
	
	
	
	
	
	@PostMapping("pay")
	@ApiOperation(value = "支付宝支付接口")
	@RequiresRoles(value={"0","1"}, logical=Logical.OR)
	public ServerResponse pay(HttpServletRequest request, Long orderNo) {
		User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		String path = request.getSession().getServletContext().getRealPath("upload");
		return orderService.pay(orderNo, user.getId(), path);
	}
	
	
	
	@PostMapping("query_order_pay_status")
	@ApiOperation(value = "查询订单支付状态接口")
	public ServerResponse<Boolean> queryOrderPayStatus(HttpServletRequest request, Long orderNo) {
		User user = jwtUserService.getCurrentUser(request);
        if(user == null) {
        	return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
		ServerResponse serverResponse = orderService.queryOrderPayStatus(user.getId(), orderNo);
		if(serverResponse.isSuccess()) {
			return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
		
	}
	
}
